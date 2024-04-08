package com.example.pme.add

import java.util.Calendar

class DataTimeManager{
    private val calendar: Calendar = Calendar.getInstance()
    var hour = calendar[Calendar.HOUR_OF_DAY]
    var minute = calendar[Calendar.MINUTE]
    var year = calendar[Calendar.YEAR]
    var month = calendar[Calendar.MONTH]
    var day = calendar[Calendar.DAY_OF_MONTH]
    fun getCurrentTime(): String {
        return "$hour:$minute"
    }

    fun getCurrentDate(): String {
        return "$day/$month/$year"
    }

}