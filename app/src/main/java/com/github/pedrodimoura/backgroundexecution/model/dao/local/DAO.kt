package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update

interface DAO<T> {

    @Insert
    fun insert(t: T)

    @Insert
    fun insertWithIdReturn(t: T) : Long

    @Insert
    fun insertWithIdReturn(ts: List<T>) : Array<Long>

    @Update
    fun update(t: T)

    @Delete
    fun delete(t: T)

}