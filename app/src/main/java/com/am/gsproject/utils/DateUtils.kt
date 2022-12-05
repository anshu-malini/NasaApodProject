package com.am.gsproject.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd"

fun getDateToday(): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US);
    return dateFormat.format(currentDate)
}