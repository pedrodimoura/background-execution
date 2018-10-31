package com.github.pedrodimoura.backgroundexecution.ui.adapter

interface IRecyclerViewAdapter<T> {

    fun add(t: T)
    fun add(ts: Collection<T>)
    fun clear()

}