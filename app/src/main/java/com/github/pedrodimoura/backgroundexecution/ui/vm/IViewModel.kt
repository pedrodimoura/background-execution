package com.github.pedrodimoura.backgroundexecution.ui.vm

import androidx.lifecycle.MutableLiveData

interface IViewModel<T> {

    fun observeData(): MutableLiveData<T>
    fun getData()

}