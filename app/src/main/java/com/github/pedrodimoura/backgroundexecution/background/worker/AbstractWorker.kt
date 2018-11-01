package com.github.pedrodimoura.backgroundexecution.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import io.reactivex.Observable

abstract class AbstractWorker<T>(context: Context, workerParameters: WorkerParameters)
    : Worker(context, workerParameters) {

    abstract fun insertData(t: T)
    abstract fun getPageAndNext(page: String): Observable<T>

}