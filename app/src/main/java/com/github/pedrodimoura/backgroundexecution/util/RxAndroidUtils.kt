package com.github.pedrodimoura.backgroundexecution.util

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

object RxAndroidUtils {

    private val sCompositeDisposable = CompositeDisposable()

    fun subscribe(disposable: Disposable) {
        sCompositeDisposable.add(disposable)
    }

    fun dispose() {
        if (!sCompositeDisposable.isDisposed)
            sCompositeDisposable.dispose()
    }

    fun <T> subscribeObserveCompose(): ObservableTransformer<T, T> {
        return ObservableTransformer {
            it
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(RxAndroidUtils::subscribe)
                .doOnDispose(RxAndroidUtils::dispose)
        }
    }

}