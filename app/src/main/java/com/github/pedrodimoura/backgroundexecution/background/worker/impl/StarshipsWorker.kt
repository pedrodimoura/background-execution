package com.github.pedrodimoura.backgroundexecution.background.worker.impl

import android.content.Context
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.background.worker.AbstractWorker
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Starships
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable
import java.lang.Exception

class StarshipsWorker(context: Context, workerParameters: WorkerParameters)
    : AbstractWorker<Starships>(context, workerParameters) {

    companion object {
        const val TAG = "starships_worker"
    }

    override fun doWork(): Result {
        return try {
            val starships = NetworkHelper.getStarshipsDAO().read().blockingFirst()

            insertData(starships)

            val nextPage: String? = UrlUtil.getNextPageFromStringUrl(starships.next!!)

            if (!nextPage.isNullOrEmpty()) {
                val starshipsRemaining = getPageAndNext(nextPage).toList().blockingGet()
                starshipsRemaining.forEach { insertData(it) }
            }

            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAILURE
        }
    }

    override fun insertData(t: Starships) {
        t.results.forEach { starship ->
            LocalHelper.instance(applicationContext)!!.getStarshipDAO().insert(starship)
        }
    }

    override fun getPageAndNext(page: String): Observable<Starships> {
        return NetworkHelper
            .getStarshipsDAO()
            .readNext(page)
            .concatMap { starships ->
                if (starships.next.isNullOrEmpty())
                    Observable.just(starships)
                else
                    Observable
                        .just(starships)
                        .concatWith(getPageAndNext(UrlUtil.getNextPageFromStringUrl(starships.next!!)))
            }
    }

}