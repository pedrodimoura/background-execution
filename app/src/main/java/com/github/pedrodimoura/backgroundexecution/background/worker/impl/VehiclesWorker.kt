package com.github.pedrodimoura.backgroundexecution.background.worker.impl

import android.content.Context
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.background.worker.AbstractWorker
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Vehicles
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable
import java.lang.Exception

class VehiclesWorker(context: Context, workerParameters: WorkerParameters)
    : AbstractWorker<Vehicles>(context, workerParameters) {

    companion object {
        const val TAG = "vehicles_worker"
    }

    override fun doWork(): Result {
        return try {
            val vehicles = NetworkHelper.getVehiclesDAO().read().blockingFirst()

            insertData(vehicles)

            val nextPage: String? = UrlUtil.getNextPageFromStringUrl(vehicles.next!!)

            if (!nextPage.isNullOrEmpty()) {
                val vehiclesRemaining = getPageAndNext(nextPage).toList().blockingGet()
                vehiclesRemaining.forEach { insertData(it) }
            }

            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAILURE
        }
    }

    override fun insertData(t: Vehicles) {
        t.results.forEach {
            vehicle -> LocalHelper.instance(applicationContext)!!.getVehicleDAO().insert(vehicle)
        }
    }

    override fun getPageAndNext(page: String): Observable<Vehicles> {
        return NetworkHelper
            .getVehiclesDAO()
            .readNext(page)
            .concatMap { vehicles ->
                if (vehicles.next.isNullOrEmpty())
                    Observable.just(vehicles)
                else
                    Observable
                        .just(vehicles)
                        .concatWith(getPageAndNext(UrlUtil.getNextPageFromStringUrl(vehicles.next!!)))
            }
    }

}