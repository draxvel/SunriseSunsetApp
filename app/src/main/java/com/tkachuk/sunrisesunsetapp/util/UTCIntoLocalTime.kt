package com.tkachuk.sunrisesunsetapp.util

import java.text.SimpleDateFormat
import java.util.*

class UTCIntoLocalTime {
    companion object {
        fun intoLocalTime(time: String): String{
            val lastIndex = if(time.substring(time.length-2).equals("AM")) " AM" else " PM"
            val df = SimpleDateFormat("hh:mm", Locale.ENGLISH)
            df.timeZone = TimeZone.getTimeZone("UTC")
            val date = df.parse(time)
            df.timeZone = TimeZone.getDefault()
            return df.format(date)+lastIndex
        }
    }
}