package com.github.pedrodimoura.backgroundexecution.model.dao.remote

import com.github.pedrodimoura.backgroundexecution.model.dto.impl.Species
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface SpeciesDAO {

    @GET(value = "species")
    fun read(): Observable<Species>

    @GET(value = "species")
    fun readNext(@Query(value = "page") page: String): Observable<Species>

}