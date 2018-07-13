package com.tkachuk.sunrisesunsetapp.ui

import com.google.android.gms.maps.model.LatLng

interface IMainContract {
    interface IMainActivity{
        fun setDataToUI(sunrise: String, sunset: String)
        fun setCityToUI(cityName: String)
        fun showMsg(str: String)
    }

    interface IMainPresenter{
        fun getData(latLng: LatLng )
        fun getDataForCurrentLocation()
    }
}