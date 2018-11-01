package com.github.pedrodimoura.backgroundexecution.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil

@Entity(
    tableName = Vehicle.TABLE_NAME,
    indices = [Index(Vehicle.COLUMN_ID)]
)
class Vehicle {

    companion object {
        const val TABLE_NAME = "vehicle"
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
    @ColumnInfo(name = "vehicle_class")
    var vehicleClass: String = ""
    var pilots: List<String> = ArrayList()
    var films: List<String> = ArrayList()
    var created: String = ""
    var edited: String = ""
    var url: String = ""

    override fun toString() = GsonUtil.toJson(this)

}