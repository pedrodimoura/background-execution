package com.github.pedrodimoura.backgroundexecution.model.dao.remote

import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Vehicles
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface VehiclesDAO {

    @GET(value = "vehicles")
    fun read(): Observable<Vehicles>

    @GET(value = "vehicles")
    fun readNext(@Query(value = "page") page: String): Observable<Vehicles>

}