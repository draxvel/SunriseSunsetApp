package com.tkachuk.sunrisesunsetapp.api

import retrofit2.http.GET
import retrofit2.http.Query

interface ServerAPI {

    //Get sunset and sunrise based on latitude and longitude
    @GET()
    fun loadData(@Query ("lat") lat: Double, @Query ("lat") long: Double)
}