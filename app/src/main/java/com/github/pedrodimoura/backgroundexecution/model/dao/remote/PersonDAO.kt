package com.github.pedrodimoura.backgroundexecution.model.dao.remote

import com.github.pedrodimoura.backgroundexecution.model.dto.impl.People
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface PersonDAO {

    @GET(value = "people")
    fun read(): Observable<People>

    @GET(value = "people")
    fun readNext(@Query(value = "page") page: String): Observable<People>

}