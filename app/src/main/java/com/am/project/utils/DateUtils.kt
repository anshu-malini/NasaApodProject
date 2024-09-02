package com.am.project.utils

import java.text.SimpleDateFormat
import java.util.*

private const val DATE_FORMAT = "yyyy-MM-dd"
private const val DATE_FORMAT_HOME = "dd MMM yyyy"
val CURRENT_YEAR = Calendar.getInstance().get(Calendar.YEAR)
val CURRENT_MONTH = Calendar.getInstance().get(Calendar.MONTH)
val CURRENT_DAY = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

fun getDateToday(): String {
    val currentDate = Date()
    val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US);
    return dateFormat.format(currentDate)
}

fun formatDateHome(date: String): String {
    val currentDateFormat = SimpleDateFormat(DATE_FORMAT, Locale.US);
    val requiredDateFormat = SimpleDateFormat(DATE_FORMAT_HOME, Locale.US);
    val newDate = currentDateFormat.parse(date)
    return requiredDateFormat.format(newDate)
}