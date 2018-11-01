package com.github.pedrodimoura.backgroundexecution.model.dao.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.pedrodimoura.backgroundexecution.model.entity.Film

@Dao
interface FilmDAO : DAO<Film> {

    @Query("SELECT * FROM film")
    fun read(): LiveData<Film>

    @Query("DELETE FROM film")
    fun deleteAll()

}