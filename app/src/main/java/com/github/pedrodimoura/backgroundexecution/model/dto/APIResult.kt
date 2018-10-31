package com.github.pedrodimoura.backgroundexecution.model.dto

import com.github.pedrodimoura.backgroundexecution.util.GsonUtil

abstract class APIResult<T> {

    var count: Int = 0
    var next: String? = null
    var previous: String? = null
    var results: List<T> = ArrayList()

    override fun toString(): String {
        return GsonUtil.toJson(this)
    }

}