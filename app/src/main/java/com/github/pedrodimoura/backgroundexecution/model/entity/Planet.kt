package com.github.pedrodimoura.backgroundexecution.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil

@Entity(
    tableName = Planet.TABLE_NAME,
    indices = [Index(Planet.COLUMN_ID)])
class Planet {

    companion object {
        const val TABLE_NAME = "planet"
        const val COLUMN_ID = "id"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0;
    var name: String = ""
    @ColumnInfo(name = "rotation_period")
    var rotationPeriod: String = ""
    @ColumnInfo(name = "orbital_period")
    var orbitalPeriod: String = ""
    var diameter: String = ""
    var climate: String = ""
    var gravity: String = ""
    var terrain: String = ""
    @ColumnInfo(name = "surface_water")
    var surfaceWater: String = ""
    var population: String = ""
    var residents: List<String> = ArrayList()
    var films: List<String> = ArrayList()
    var created: String = ""
    var edited: String = ""
    var url: String = ""

    override fun toString(): String {
        return GsonUtil.toJson{this}
    }

}