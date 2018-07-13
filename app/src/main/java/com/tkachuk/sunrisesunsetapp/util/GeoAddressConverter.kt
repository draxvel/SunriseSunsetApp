package com.tkachuk.sunrisesunsetapp.util

import android.content.Context
import android.location.Geocoder
import com.google.android.gms.maps.model.LatLng
import java.util.*

object GeoAddressConverter {
        fun geoToAddress(latLng: LatLng, context: Context): String {
            var address = ""
            try {
                val geo = Geocoder(context, Locale.getDefault())
                val addresses = geo.getFromLocation(latLng.latitude, latLng.longitude, 1)
                address = if (addresses.isEmpty()) {
                    ""
                } else {
                    addresses[0].locality
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return if(address=="") "your city" else address
        }
}