package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.pedrodimoura.backgroundexecution.model.entity.Vehicle

@Dao
interface VehicleDAO : DAO<Vehicle> {

    @Query("SELECT * FROM vehicle")
    fun read(): LiveData<Vehicle>

    @Query("DELETE FROM vehicle")
    fun deleteAll()

}