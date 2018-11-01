package com.github.pedrodimoura.backgroundexecution.background.worker.impl

import android.content.Context
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.background.worker.AbstractWorker
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Planets
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable
import java.lang.Exception

class PlanetsWorker(context: Context, workerParameters: WorkerParameters)
    : AbstractWorker<Planets>(context, workerParameters) {

    companion object {
        const val TAG = "planets_worker"
    }

    override fun doWork(): Result {
        return try {
            val planets = NetworkHelper.getPlanetsDAO().read().blockingFirst()

            insertData(planets)

            val nextPage: String? = UrlUtil.getNextPageFromStringUrl(planets.next!!)

            if (!nextPage.isNullOrEmpty()) {
                val planetsRemaining = getPageAndNext(nextPage).toList().blockingGet()
                planetsRemaining.forEach { insertData(it) }
            }

            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAILURE
        }
    }

    override fun insertData(t: Planets) {
        t.results.forEach {
                planet -> LocalHelper.instance(applicationContext)!!.getPlanetDAO().insert(planet)
        }
    }

    override fun getPageAndNext(page: String): Observable<Planets> {
        return NetworkHelper
            .getPlanetsDAO()
            .readNext(page)
            .concatMap { planets ->
                if (planets.next.isNullOrEmpty()) {
                    Observable.just(planets)
                } else {
                    Observable
                        .just(planets)
                        .concatWith(getPageAndNext(UrlUtil.getNextPageFromStringUrl(planets.next!!)))
                }
            }
    }

}