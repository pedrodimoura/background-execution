package com.github.pedrodimoura.backgroundexecution.model.dao.local.converter

import androidx.room.TypeConverter
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListConverter {

    private inline fun <reified T> Gson.fromJson(t: String) =
        this.fromJson<List<T>>(t, object : TypeToken<List<T>>(){}.type)!!

    @TypeConverter
    fun toList(json: String): List<String> {
        return Gson().fromJson(json)
    }

    @TypeConverter
    fun toJson(collection: List<String>): String {
        return GsonUtil.toJson(collection)
    }

}