package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.pedrodimoura.backgroundexecution.model.entity.Starship

@Dao
interface StarshipDAO : DAO<Starship> {

    @Query("SELECT * FROM starship")
    fun read(): LiveData<Starship>

    @Query("DELETE FROM starship")
    fun deleteAll()

}