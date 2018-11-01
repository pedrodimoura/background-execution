package com.github.pedrodimoura.backgroundexecution.background.worker.impl

import android.content.Context
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.background.worker.AbstractWorker
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.People
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable
import java.lang.Exception

class PeopleWorker(context: Context, workerParameters: WorkerParameters)
    : AbstractWorker<People>(context, workerParameters) {

    companion object {
        const val TAG = "people_work"
    }

    override fun doWork(): Result {
        return try {
            val people = NetworkHelper.getPeopleDAO().read().blockingFirst()

            insertData(people)

            val nextPage: String? = UrlUtil.getNextPageFromStringUrl(people.next!!)

            if (!nextPage.isNullOrEmpty()) {
                val peopleRemaining = getPageAndNext(nextPage).toList().blockingGet()
                peopleRemaining.forEach { insertData(it) }
            }

            Result.SUCCESS
        } catch (e: Exception) {
            Result.FAILURE
        }
    }

    override fun insertData(t: People) {
        t.results.forEach { person ->
            LocalHelper.instance(applicationContext)!!.getPersonDAO().insert(person)
        }
    }

    override fun getPageAndNext(page: String): Observable<People> {
        return NetworkHelper
            .getPeopleDAO()
            .readNext(page)
            .concatMap { people ->
                if (people.next.isNullOrEmpty()) {
                    Observable.just(people)
                } else {
                    Observable
                        .just(people)
                        .concatWith(getPageAndNext(UrlUtil.getNextPageFromStringUrl(people.next!!)))
                }
            }
    }

}