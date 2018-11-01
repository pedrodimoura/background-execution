package com.github.pedrodimoura.backgroundexecution.background.worker.impl

import android.content.Context
import android.util.Log
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.background.worker.AbstractWorker
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Species
import com.github.pedrodimoura.backgroundexecution.util.Constants
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable
import java.lang.Exception

class SpeciesWorker(context: Context, workerParameters: WorkerParameters)
    : AbstractWorker<Species>(context, workerParameters) {

    companion object {
        const val TAG = "species_worker"
    }

    override fun doWork(): Result {
        return try {
            val species = NetworkHelper.getSpeciesDAO().read().blockingFirst()

            insertData(species)

            val nextPage: String? = UrlUtil.getNextPageFromStringUrl(species.next!!)

            if (!nextPage.isNullOrEmpty()) {
                val speciesRemaining = getPageAndNext(nextPage).toList().blockingGet()
                speciesRemaining.forEach { insertData(it) }
            }

            Result.SUCCESS
        } catch (e: Exception) {
            Log.d(Constants.DEBUG_TAG, e.message)
            Result.FAILURE
        }
    }

    override fun insertData(t: Species) {
        t.results.forEach {
            specie -> LocalHelper.instance(applicationContext)!!.getSpecieDAO().insert(specie)
        }
    }

    override fun getPageAndNext(page: String): Observable<Species> {
        return NetworkHelper
            .getSpeciesDAO()
            .readNext(page)
            .concatMap { species ->
                if (species.next.isNullOrEmpty())
                    Observable.just(species)
                else
                    Observable
                        .just(species)
                        .concatWith(getPageAndNext(UrlUtil.getNextPageFromStringUrl(species.next!!)))
            }
    }

}