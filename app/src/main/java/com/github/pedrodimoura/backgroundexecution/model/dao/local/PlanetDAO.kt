package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.pedrodimoura.backgroundexecution.model.entity.Planet

@Dao
interface PlanetDAO: DAO<Planet> {

    @Query("SELECT * FROM planet")
    fun read(): LiveData<List<Planet>>

    @Query("DELETE FROM planet")
    fun deleteAll()

}