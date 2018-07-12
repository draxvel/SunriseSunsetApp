package com.tkachuk.sunrisesunsetapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//Get only properties which we use
@JsonIgnoreProperties(ignoreUnknown = true)
class SunriseSunset {
    var sunrise: Double = 0.0
    var sunset: Double = 0.0
}