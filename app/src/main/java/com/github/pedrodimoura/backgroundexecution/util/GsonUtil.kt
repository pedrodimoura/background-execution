package com.github.pedrodimoura.backgroundexecution.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object GsonUtil {

    fun toJson(t: Any): String {
        return Gson().toJson(t)
    }

    fun fromJson(t: String, clazz: Class<Unit>): Any {
        return Gson().fromJson(t, clazz)
    }

}