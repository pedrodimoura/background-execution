package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.pedrodimoura.backgroundexecution.model.entity.Person

@Dao
interface PersonDAO : DAO<Person> {

    @Query("SELECT * FROM person")
    fun read(): LiveData<List<Person>>

    @Query("DELETE FROM person")
    fun deleteAll()

}