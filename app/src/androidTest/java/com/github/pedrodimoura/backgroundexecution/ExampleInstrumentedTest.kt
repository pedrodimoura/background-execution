package com.github.pedrodimoura.backgroundexecution

import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.github.pedrodimoura.backgroundexecution.background.worker.PeopleWorker
import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4ClassRunner::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().context

        WorkManager.initialize(appContext, Configuration.Builder().build())

        val oneTimeWorkRequest = OneTimeWorkRequest.Builder(PeopleWorker::class.java).build()

        WorkManager.getInstance().enqueue(oneTimeWorkRequest)

    }
}
