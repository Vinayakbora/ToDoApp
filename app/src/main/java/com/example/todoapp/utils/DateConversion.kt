package com.example.todoapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertStringToDate(strDate: String, format: String = "dd-mm-yy   hh:mm"): Date? {
    return SimpleDateFormat(format, Locale.getDefault()).parse(strDate)
}
