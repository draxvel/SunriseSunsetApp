package com.tkachuk.sunrisesunsetapp.ui

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import com.tkachuk.sunrisesunsetapp.data.ISunriseSunsetData
import com.tkachuk.sunrisesunsetapp.data.SunriseSunsetData
import com.tkachuk.sunrisesunsetapp.models.SunriseSunset
import com.tkachuk.sunrisesunsetapp.util.GeoAddressConverter
import com.tkachuk.sunrisesunsetapp.util.GpsTracker

class MainPresenter(iMainActivity: IMainContract.IMainActivity) : IMainContract.IMainPresenter {
    private val view: IMainContract.IMainActivity = iMainActivity

    private val sunriseSunsetData: SunriseSunsetData = SunriseSunsetData()
    private val gpsTracker: GpsTracker
    init {
        gpsTracker = GpsTracker(view as Context)


    }

    override fun getData(latLng: LatLng) {
        sunriseSunsetData.getSunriseSunset(latLng.latitude, latLng.longitude, "today",
                getSunriseSunsetCallBack = object : ISunriseSunsetData.GetSunriseSunsetCallBack {
                    override fun onGet(ss: SunriseSunset) {
                        view.setDataToUI(ss.results.sunrise, ss.results.sunset)
                    }

                    override fun onError(error: String) {
                        view.showMsg(error)
                    }
                })
    }

    override fun getDataForCurrentLocation(){
        if(gpsTracker.canGetLocation()){
            if(gpsTracker.getLatitude() == 0.0 && gpsTracker.getLongitude()==0.0){
                gpsTracker.getLocation()
            }
            getData(LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()))
            view.setCityToUI(GeoAddressConverter.geoToAddress(LatLng(gpsTracker.getLatitude(), gpsTracker.getLongitude()), view as Context))
            gpsTracker.stopUsingGPS()
        }else{
            gpsTracker.showSettingsAlert()
        }
    }
}