package com.github.pedrodimoura.backgroundexecution.ui.vm.impl

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl.NetworkHelper
import com.github.pedrodimoura.backgroundexecution.model.dto.impl.People
import com.github.pedrodimoura.backgroundexecution.ui.vm.IViewModel
import com.github.pedrodimoura.backgroundexecution.util.RxAndroidUtils

class PeopleViewModel : ViewModel(), IViewModel<People> {

    private var mPersonMutableLiveData: MutableLiveData<People> = MutableLiveData()

    override fun observeData(): MutableLiveData<People> {
        return mPersonMutableLiveData
    }

    override fun getData() {
        NetworkHelper
            .getPeopleDAO()
            .read()
            .compose(RxAndroidUtils.subscribeObserveCompose())
            .doOnNext {mPersonMutableLiveData.postValue(it)}
            .subscribe()
    }
}