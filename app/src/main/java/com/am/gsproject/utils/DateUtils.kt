package com.am.gsproject.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd"
val CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR)
val CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH)
val CURRENT_DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun getDateToday(): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US);
    return dateFormat.format(currentDate)
}