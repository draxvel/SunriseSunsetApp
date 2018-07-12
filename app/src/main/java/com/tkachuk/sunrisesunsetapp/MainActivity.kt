package com.tkachuk.sunrisesunsetapp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.tkachuk.sunrisesunsetapp.models.SunriseSunset
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.tkachuk.sunrisesunsetapp.data.ISunriseSunsetData
import com.tkachuk.sunrisesunsetapp.data.SunriseSunsetData
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var sunriseSunsetData: SunriseSunsetData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        sunriseSunsetData = SunriseSunsetData()

        setAutoComplete()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun setAutoComplete(){
        val autocompleteFragment: PlaceAutocompleteFragment = fragmentManager.findFragmentById(R.id.searchView) as PlaceAutocompleteFragment

        val typeFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build()
        autocompleteFragment.setFilter(typeFilter)

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {

                tv_result_city.text = getString(R.string.description_to_tv, place.name)

                sunriseSunsetData.getSunriseSunset(place.latLng.latitude, place.latLng.longitude, "today",
                        getSunriseSunsetCallBack = object: ISunriseSunsetData.GetSunriseSunsetCallBack{
                            override fun onGet(ss: SunriseSunset) {
                                tv_sunrise.text = ss.results.sunrise
                                tv_sunset.text = ss.results.sunset
                            }

                            override fun onError(error: String) {
                                showMsg(error)
                            }
                        } )
            }

            override fun onError(p0: Status?) {
                showMsg(p0.toString())
            }
        })
    }

    private fun showMsg(str: String){
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }
}
