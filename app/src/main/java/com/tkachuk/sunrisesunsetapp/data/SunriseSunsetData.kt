package com.tkachuk.sunrisesunsetapp.data

import com.tkachuk.sunrisesunsetapp.api.ServiceGenerator
import com.tkachuk.sunrisesunsetapp.models.SunriseSunset
import com.tkachuk.sunrisesunsetapp.util.UTCIntoLocalTime
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch

class SunriseSunsetData : ISunriseSunsetData{
    override fun getSunriseSunset(lat: Double, lng: Double, date: String, getSunriseSunsetCallBack: ISunriseSunsetData.GetSunriseSunsetCallBack) {
        launch(UI) {
            try {
                val ss: SunriseSunset = ServiceGenerator.serverAPI.loadData(lat, lng, date).await()
                ss.results.sunrise = UTCIntoLocalTime.intoLocalTime(ss.results.sunrise)
                ss.results.sunset = UTCIntoLocalTime.intoLocalTime(ss.results.sunset)
                getSunriseSunsetCallBack.onGet(ss)
            } catch (text: Exception) {
                getSunriseSunsetCallBack.onError("Check Internet connection")
            }
        }
    }
}