package com.github.pedrodimoura.backgroundexecution.model.dao.remote

import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Films
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface FilmsDAO {

    @GET(value = "films")
    fun read(): Observable<Films>

    @GET(value = "films")
    fun readNext(@Query(value = "page") page: String): Observable<Films>

}