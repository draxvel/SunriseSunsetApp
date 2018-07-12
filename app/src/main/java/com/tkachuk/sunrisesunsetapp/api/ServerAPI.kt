package com.tkachuk.sunrisesunsetapp.api

import com.tkachuk.sunrisesunsetapp.models.SunriseSunset
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerAPI {

    //Get sunset and sunrise based on latitude and longitude
    //Deferred - for works with Coroutine
    @GET("json")
    fun loadData(@Query ("lat") lat: Double, @Query ("lng") lng: Double, @Query("date") date: String)
            : Deferred<SunriseSunset>
}