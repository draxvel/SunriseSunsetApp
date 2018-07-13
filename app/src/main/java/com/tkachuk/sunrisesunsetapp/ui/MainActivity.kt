package com.tkachuk.sunrisesunsetapp.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.places.Place
import com.google.android.gms.location.places.ui.PlaceSelectionListener
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment
import com.tkachuk.sunrisesunsetapp.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), IMainContract.IMainActivity {

    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setAutoComplete()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main_activity, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if(item.itemId == R.id.item_get_my_location){
            presenter.getDataForCurrentLocation()
            true
        }else super.onOptionsItemSelected(item)
    }

    override fun onStart() {
        super.onStart()
        presenter = MainPresenter(this)
    }

    override fun setDataToUI(sunrise: String, sunset: String) {
        tv_sunrise.text = sunrise
        tv_sunset.text = sunset
    }

    override fun setCityToUI(cityName: String) {
        tv_result_city.text = getString(R.string.description_to_tv, cityName)
    }

    private fun setAutoComplete(){
        val autocompleteFragment: PlaceAutocompleteFragment = fragmentManager.findFragmentById(R.id.searchView)
                as PlaceAutocompleteFragment

        val typeFilter = AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_CITIES)
                .build()
        autocompleteFragment.setFilter(typeFilter)

        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                setCityToUI(place.name.toString())
                presenter.getData(place.latLng)
            }

            override fun onError(p0: Status?) {
                showMsg(p0.toString())
            }
        })
    }

    override fun showMsg(str: String){
        Toast.makeText(applicationContext, str, Toast.LENGTH_SHORT).show()
    }
}
