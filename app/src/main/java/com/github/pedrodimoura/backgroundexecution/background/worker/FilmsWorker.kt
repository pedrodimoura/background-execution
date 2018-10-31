package com.github.pedrodimoura.backgroundexecution.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class FilmsWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    override fun doWork(): Result {
        return Result.SUCCESS
    }

}