package com.github.pedrodimoura.backgroundexecution.background.worker.impl

import android.content.Context
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.background.worker.AbstractWorker
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Films
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable
import java.lang.Exception

class FilmsWorker(context: Context, workerParameters: WorkerParameters)
    : AbstractWorker<Films>(context, workerParameters) {

    companion object {
        const val TAG = "films_worker"
    }

    override fun doWork(): Result {
        return try {
            val films = NetworkHelper.getFilmsDAO().read().blockingFirst()

            insertData(films)

            if (!films.next.isNullOrEmpty()) {
                val nextPage = UrlUtil.getNextPageFromStringUrl(films.next!!)
                val filmsRemaining = getPageAndNext(nextPage).toList().blockingGet()
                filmsRemaining.forEach { insertData(it) }
            }
            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAILURE
        }
    }

    override fun insertData(t: Films) {
        t.results.forEach {
                film -> LocalHelper.instance(applicationContext)!!.getFilmDAO().insert(film)
        }
    }

    override fun getPageAndNext(page: String): Observable<Films> {
        return NetworkHelper
            .getFilmsDAO()
            .readNext(page)
            .concatMap { films ->
                if (films.next.isNullOrEmpty()) {
                    Observable.just(films)
                } else {
                    Observable
                        .just(films)
                        .concatWith(getPageAndNext(UrlUtil.getNextPageFromStringUrl(films.next!!)))
                }
            }
    }

}