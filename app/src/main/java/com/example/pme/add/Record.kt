package com.example.pme.add

data class Record(var id:Int?, val date:String, val time:String, val systolic:Int, val diastolic:Int, val pulse:Int) {
    val dateTime="$date, $time"
}
