package com.tkachuk.sunrisesunsetapp.models

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

//Get only properties which we use
@JsonIgnoreProperties(ignoreUnknown = true)
class Results {
    lateinit var sunrise: String
    lateinit var sunset: String

    override fun toString(): String {
        return "Results(sunrise='$sunrise', sunset='$sunset')"
    }
}