package com.github.pedrodimoura.backgroundexecution.background.worker

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.github.pedrodimoura.backgroundexecution.model.dao.local.impl.LocalHelper
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.People
import com.github.pedrodimoura.backgroundexecution.util.UrlUtil
import io.reactivex.Observable

class PeopleWorker(context: Context, workerParameters: WorkerParameters) : Worker(context, workerParameters) {

    companion object {
        const val TAG = "people_work"
    }

    override fun doWork(): Result {

        val people = NetworkHelper.getPeopleDAO().read().blockingFirst()

        insertPeople(people)

        val nextPage = UrlUtil.getNextPageFromStringUrl(people.next!!)

        val peopleRemaining = getPageAndNext(nextPage).toList().blockingGet()

        peopleRemaining.forEach { insertPeople(it) }

        return Result.SUCCESS
    }

    private fun insertPeople(people: People) {
        people.results.forEach { person ->
            LocalHelper.instance(applicationContext)!!.getPersonDAO().insert(person)
        }
    }

    private fun getPageAndNext(page: String): Observable<People> {
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