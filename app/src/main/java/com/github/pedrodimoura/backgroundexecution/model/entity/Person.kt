package com.github.pedrodimoura.backgroundexecution.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil

@Entity(
    tableName = Person.TABLE_NAME,
    indices = [Index(Person.COLUMN_ID)])
class Person {

    companion object {
        const val TABLE_NAME = "person"
        const val COLUMN_ID = "id"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0
    var name: String = ""
    var mass: String = ""
    @ColumnInfo(name = "hair_color")
    var hairColor: String = ""
    @ColumnInfo(name = "skin_color")
    var skinColor: String = ""
    @ColumnInfo(name = "eye_color")
    var eyeColor: String = ""
    @ColumnInfo(name = "birth_year")
    var birthYear: String = ""
    var gender: String = ""
    @ColumnInfo(name = "home_world")
    var homeworld: String = ""
    var films: List<String> = ArrayList()
    var species: List<String> = ArrayList()
    var vehicles: List<String> = ArrayList()
    var starships: List<String> = ArrayList()
    var created: String = ""
    var edited: String = ""
    var url: String = ""

    override fun toString() = GsonUtil.toJson(this)

}