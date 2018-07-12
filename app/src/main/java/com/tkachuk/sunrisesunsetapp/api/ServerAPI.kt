package com.tkachuk.sunrisesunsetapp.api

import com.tkachuk.sunrisesunsetapp.models.SunriseSunset
import kotlinx.coroutines.experimental.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface ServerAPI {

    //Get sunset and sunrise based on latitude and longitude
    //Deferred - for works with Coroutine
    @GET("json")
    fun loadData(@Query ("lat") lat: Float, @Query ("lng") lng: Float, @Query("date") date: String)
            : Deferred<SunriseSunset>
}