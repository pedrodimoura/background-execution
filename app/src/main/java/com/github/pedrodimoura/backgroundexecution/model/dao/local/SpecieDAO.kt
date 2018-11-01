package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.pedrodimoura.backgroundexecution.model.entity.Specie

@Dao
interface SpecieDAO : DAO<Specie> {

    @Query("SELECT * FROM specie")
    fun read(): LiveData<Specie>

    @Query("DELETE FROM specie")
    fun deleteAll()

}