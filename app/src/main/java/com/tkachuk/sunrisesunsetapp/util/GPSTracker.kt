package com.tkachuk.sunrisesunsetapp.util

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.os.IBinder
import android.provider.Settings
import android.support.v4.app.ActivityCompat
import android.support.v7.app.AlertDialog
import android.support.v7.view.ContextThemeWrapper
import android.util.Log
import com.tkachuk.sunrisesunsetapp.R

class GpsTracker(c: Context) : Service(), LocationListener {

    private val context: Context = c

    // The minimum distance to change Updates in meters
    private val MIN_DISTANCE_CHANGE_FOR_UPDATES: Long = 10 // 10 meters

    // The minimum time between updates in milliseconds
    private val MIN_TIME_BW_UPDATES = (1000 * 60).toLong() // 1 minute

    // flag for GPS status
    private var isGPSEnabled = false

    // flag for network status
    private var isNetworkEnabled = false

    // flag for GPS status
    private var canGetLocation = false

    private var location: Location? = null // location
    private var latitude: Double = 0.toDouble() // latitude
    private var longitude: Double = 0.toDouble() // longitude

    // Declaring a Location Manager
    private var locationManager: LocationManager? = null

    init {
        getLocation()
    }

    fun getLocation(): Location? {
        try {
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

            // getting GPS status
            if (locationManager != null) {
                isGPSEnabled = locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)
            }

            // getting network status
            if (locationManager != null) {
                isNetworkEnabled = locationManager!!.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
            }

            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            } else {
                this.canGetLocation = true
                // First get location from Network Provider
                if (isNetworkEnabled) {
                    //check the network permission
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
                    }
                    locationManager!!.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)
                        Log.d("Network", "Network")

                        if (locationManager != null) {
                            location = locationManager!!
                                    .getLastKnownLocation(LocationManager.NETWORK_PROVIDER)

                            if (location != null) {
                                latitude = location!!.latitude
                                longitude = location!!.longitude
                            }
                        }
                    }

                    // if GPS Enabled get lat/long using GPS Services
                    if (isGPSEnabled) {
                        if (location == null) {
                            //check the network permission
                            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(context as Activity, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), 101)
                            }
                            locationManager!!.requestLocationUpdates(
                                    LocationManager.GPS_PROVIDER,
                                    MIN_TIME_BW_UPDATES,
                                    MIN_DISTANCE_CHANGE_FOR_UPDATES.toFloat(), this)

                            Log.d("GPS Enabled", "GPS Enabled")
                            if (locationManager != null) {
                                location = locationManager!!
                                        .getLastKnownLocation(LocationManager.GPS_PROVIDER)

                                if (location != null) {
                                    latitude = location!!.latitude
                                    longitude = location!!.longitude
                                }
                            }
                        }
                    }
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }

            return location
        }

        /**
         * Stop using GPS listener
         * Calling this function will stop using GPS in your app
         */

        fun stopUsingGPS() {
            if (locationManager != null) {
                locationManager!!.removeUpdates(this@GpsTracker)
            }
        }

        /**
         * Function to get latitude
         */

        fun getLatitude(): Double {
            if (location != null) {
                latitude = location!!.latitude
            }

            // return latitude
            return latitude
        }

        /**
         * Function to get longitude
         */

        fun getLongitude(): Double {
            if (location != null) {
                longitude = location!!.longitude
            }

            // return longitude
            return longitude
        }

        /**
         * Function to check GPS/wifi enabled
         * @return boolean
         */

        fun canGetLocation(): Boolean {
            return this.canGetLocation
        }

        /**
         * Function to show settings alert dialog
         * On pressing Settings button will lauch Settings Options
         */

        fun showSettingsAlert() {
            val alertDialog =
                    AlertDialog
                            .Builder(ContextThemeWrapper(context, R.style.Theme_AppCompat_Light_Dialog_Alert))

            alertDialog.setCancelable(false)

            // Setting Dialog Message
            alertDialog.setMessage("GPS is not enabled. Please turn on.")

            // On pressing Settings button
            alertDialog.setPositiveButton("Settings") { _, which ->
                val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                context.startActivity(intent)
            }

            alertDialog.show()
        }

        override fun onLocationChanged(location: Location) {}

        override fun onProviderDisabled(provider: String) {}

        override fun onProviderEnabled(provider: String) {}

        override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

        override fun onBind(arg0: Intent): IBinder? {
            return null
        }
}