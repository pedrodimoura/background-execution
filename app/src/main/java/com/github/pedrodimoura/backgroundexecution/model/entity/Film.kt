package com.github.pedrodimoura.backgroundexecution.model.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.github.pedrodimoura.backgroundexecution.util.GsonUtil

@Entity(
    tableName = Film.TABLE_NAME,
    indices = [Index(Film.COLUMN_ID)]
)
class Film {

    companion object {
        const val TABLE_NAME = "film"
        const val COLUMN_ID = "id"
    }

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0;
    var title: String = ""
    @ColumnInfo(name = "episode_id")
    var episodeId: Int = 0
    @ColumnInfo(name = "opening_crawl")
    var openingCrawl: String = ""
    var director: String = ""
    var producer: String = ""
    @ColumnInfo(name = "release_date")
    var releaseDate: String = ""
    var characters: List<String> = ArrayList()
    var planets: List<String> = ArrayList()
    var starships: List<String> = ArrayList()
    var vehicles: List<String> = ArrayList()
    var species: List<String> = ArrayList()
    var created: String = ""
    var edited: String = ""
    var url: String = ""

    override fun toString() = GsonUtil.toJson(this)

}