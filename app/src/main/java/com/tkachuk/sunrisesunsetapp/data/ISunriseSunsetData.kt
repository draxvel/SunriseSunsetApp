package com.tkachuk.sunrisesunsetapp.data

import com.tkachuk.sunrisesunsetapp.models.SunriseSunset

interface ISunriseSunsetData {

    interface  GetSunriseSunsetCallBack{
        fun onGet(ss: SunriseSunset)
        fun onError(error: String)
    }

    fun getSunriseSunset(lat: Double, lng: Double, date: String, getSunriseSunsetCallBack: GetSunriseSunsetCallBack)
}