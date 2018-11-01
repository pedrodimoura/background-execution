package com.github.pedrodimoura.backgroundexecution.model.dao.remote.impl

import com.github.pedrodimoura.backgroundexecution.model.dao.remote.*
import com.google.gson.FieldNamingPolicy
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkHelper {

    private const val BASE_URL = "https://swapi.co/api/"

    private fun retrofit(): Retrofit {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val okHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(
                GsonBuilder()
                    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                    .create()
            ))
            .client(okHttpClient)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createAsync())
            .build()
    }

    fun getPeopleDAO(): PersonDAO = retrofit().create(PersonDAO::class.java)
    fun getPlanetsDAO(): PlanetsDAO = retrofit().create(PlanetsDAO::class.java)
    fun getFilmsDAO(): FilmsDAO = retrofit().create(FilmsDAO::class.java)
    fun getSpeciesDAO(): SpeciesDAO = retrofit().create(SpeciesDAO::class.java)
    fun getVehiclesDAO(): VehiclesDAO = retrofit().create(VehiclesDAO::class.java)
    fun getStarshipsDAO(): StarshipsDAO = retrofit().create(StarshipsDAO::class.java)

}