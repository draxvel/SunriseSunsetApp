package com.tkachuk.sunrisesunsetapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//Get only properties which we use
@JsonIgnoreProperties(ignoreUnknown = true)
class SunriseSunset {
    lateinit var results: Results
    lateinit var status: String

    override fun toString(): String {
        return "SunriseSunset(status='$status')"
    }
}
