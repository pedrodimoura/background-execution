package com.github.pedrodimoura.backgroundexecution.ui.activity.impl

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProviders
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.github.pedrodimoura.backgroundexecution.R
import com.github.pedrodimoura.backgroundexecution.background.worker.PeopleWorker
import com.github.pedrodimoura.backgroundexecution.background.worker.PlanetsWorker
import com.github.pedrodimoura.backgroundexecution.ui.activity.IView
import com.github.pedrodimoura.backgroundexecution.ui.vm.impl.PeopleViewModel

class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener,
    IView {

    private lateinit var mSearchView: SearchView
    private lateinit var mPeopleViewModel: PeopleViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        observeData()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val menuItem    = menu?.findItem(R.id.menuMainSearch)
        mSearchView     = menuItem?.actionView as SearchView
        mSearchView.setOnQueryTextListener(this)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return true
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        return true
    }

    override fun init() {
        this.mPeopleViewModel = ViewModelProviders.of(this).get(PeopleViewModel::class.java)

//        val peopleWorkerRequest =
//            OneTimeWorkRequest.Builder(PeopleWorker::class.java).addTag(PeopleWorker.TAG).build()
//
//        WorkManager.getInstance().enqueue(peopleWorkerRequest)
    }

    override fun observeData() {
        val peopleWorkRequest = OneTimeWorkRequest.Builder(PeopleWorker::class.java).addTag(PeopleWorker.TAG).build()
        val planetsWorkRequest = OneTimeWorkRequest.Builder(PlanetsWorker::class.java).addTag(PlanetsWorker.TAG).build()

        WorkManager
            .getInstance()
            .enqueue(
                peopleWorkRequest,
                planetsWorkRequest
            )
//        this.mPeopleViewModel.getData()
//        this.mPeopleViewModel.observeData().observeForever{
//            it.results.forEach { person ->
//                Log.d(Constants.DEBUG_TAG, "Person: ${person.toString()}")
//            }
//        }
    }

}
