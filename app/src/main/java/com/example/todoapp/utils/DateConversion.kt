package com.example.todoapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertStringToDate(strDate: String, format: String = "dd-MM-yyyy  HH:mm"): Date? {
    return SimpleDateFormat(format, Locale.getDefault()).parse(strDate)
}
