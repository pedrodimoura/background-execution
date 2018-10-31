package com.github.pedrodimoura.backgroundexecution.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Planets
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable

class PlanetsWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object {
        const val TAG = "planets_worker"
    }

    override fun doWork(): Result {

        val planets = NetworkHelper.getPlanetsDAO().read().blockingFirst()

        insertPlanets(planets)

        val next = UrlUtil.getNextPageFromStringUrl(planets.next!!)

        val planetsRemaining = getPageAndNext(next).toList().blockingGet()

        planetsRemaining.forEach { insertPlanets(it) }

        return Result.SUCCESS
    }

    private fun insertPlanets(planets: Planets) {
        planets.results.forEach {
                planet -> LocalHelper.instance(applicationContext)!!.getPlanetDAO().insert(planet)
        }
    }

    private fun getPageAndNext(page: String): Observable<Planets> {
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