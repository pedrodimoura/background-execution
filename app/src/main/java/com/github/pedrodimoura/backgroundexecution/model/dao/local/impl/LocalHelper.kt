package com.github.pedrodimoura.backgroundexecution.model.dao.local.impl

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.pedrodimoura.backgroundexecution.model.dao.local.*
import com.github.pedrodimoura.backgroundexecution.model.dao.local.converter.ListConverter
import com.github.pedrodimoura.backgroundexecution.model.entity.*

@Database(
    entities = [
        Person::class,
        Planet::class,
        Film::class,
        Specie::class,
        Vehicle::class,
        Starship::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(value = [ListConverter::class])
abstract class LocalHelper : RoomDatabase() {

    companion object {

        private const val DATABASE_NAME = "star_wars"
        private var sInstance: LocalHelper? = null

        fun instance(context: Context) : LocalHelper? {
            if (sInstance == null) {
                synchronized(LocalHelper::class) {
                    if (sInstance == null) {
                        sInstance = Room.databaseBuilder(context, LocalHelper::class.java, DATABASE_NAME).build()
                    }
                }
            }
            return sInstance
        }

    }

    abstract fun getPersonDAO(): PersonDAO
    abstract fun getPlanetDAO(): PlanetDAO
    abstract fun getFilmDAO(): FilmDAO
    abstract fun getSpecieDAO(): SpecieDAO
    abstract fun getVehicleDAO(): VehicleDAO
    abstract fun getStarshipDAO(): StarshipDAO

}