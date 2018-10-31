package com.github.pedrodimoura.backgroundexecution.model.dao.remote

import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Planets
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PlanetsDAO {

    @GET(value = "planets")
    fun read(): Observable<Planets>

    @GET(value = "planets")
    fun readNext(@Query(value = "page") page: String): Observable<Planets>

}