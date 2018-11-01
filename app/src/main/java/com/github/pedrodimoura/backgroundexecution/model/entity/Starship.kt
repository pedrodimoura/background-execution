package com.github.pedrodimoura.backgroundexecution.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil
import com.google.gson.annotations.SerializedName

@Entity(
    tableName = Starship.TABLE_NAME,
    indices = [Index(Starship.COLUMN_ID)]
)
class Starship {

    companion object {
        const val TABLE_NAME = "starship"
        const val COLUMN_ID = "id"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = COLUMN_ID)
    var id: Int = 0
    var name: String = ""
    var model: String = ""
    var manufacturer: String = ""
    @ColumnInfo(name = "cost_in_credits")
    var costInCredits: String = ""
    var length: String = ""
    @ColumnInfo(name = "max_atmosphering_speed")
    var maxAtmospheringSpeed: String = ""
    var crew: String = ""
    var passengers: String = ""
    @ColumnInfo(name = "cargo_capacity")
    var cargoCapacity: String = ""
    var consumables: String = ""
    @ColumnInfo(name = "hyperdrive_rating")
    var hyperdriveRating: String = ""
    @SerializedName("MGLT")
    var mglt: String = ""
    @ColumnInfo(name = "starship_class")
    var starshipClass: String = ""
    var pilots: List<String> = ArrayList()
    var films: List<String> = ArrayList()
    var created: String = ""
    var edited: String = ""
    var url: String = ""

    override fun toString() = GsonUtil.toJson(this)

}