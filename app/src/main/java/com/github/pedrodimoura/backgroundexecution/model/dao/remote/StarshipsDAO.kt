package com.github.pedrodimoura.backgroundexecution.model.dao.remote

import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Starships
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface StarshipsDAO {

    @GET(value = "starships")
    fun read(): Observable<Starships>

    @GET(value = "starships")
    fun readNext(@Query("page") page: String): Observable<Starships>

}