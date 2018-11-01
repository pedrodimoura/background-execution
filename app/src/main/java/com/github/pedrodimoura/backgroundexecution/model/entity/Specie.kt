package com.github.pedrodimoura.backgroundexecution.model.entity

import androidx.room.*
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil

@Entity(
    tableName = Specie.TABLE_NAME,
    indices = [Index(Specie.COLUMN_ID)]
)
class Specie {

    companion object {
        const val TABLE_NAME = "specie"
        const val COLUMN_ID = "id"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0
    var name: String = ""
    var classification: String = ""
    var designation: String = ""
    @ColumnInfo(name = "average_height")
    var averageHeight: String = ""
    @ColumnInfo(name = "skin_colors")
    var skinColors: String = ""
    @ColumnInfo(name = "hair_colors")
    var hairColors: String = ""
    @ColumnInfo(name = "eye_colors")
    var eyeColors: String = ""
    @ColumnInfo(name = "average_lifespan")
    var averageLifespan: String = ""
    var homeworld: String? = ""
    var language: String = ""
    var people: List<String> = ArrayList()
    var films: List<String> = ArrayList()
    var created: String = ""
    var edited: String = ""
    var url: String = ""

    override fun toString() = GsonUtil.toJson(this)

}