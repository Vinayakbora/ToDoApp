package com.example.todoapp.utils

import java.text.SimpleDateFormat
import java.util.*

fun convertStringToDate(strDate: String, format: String = "dd-mm-yy"): Date? {
    return SimpleDateFormat(format, Locale.getDefault()).parse(strDate)
}
