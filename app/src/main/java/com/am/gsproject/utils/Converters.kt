package com.am.gsproject.utils

import androidx.room.TypeConverter
import com.am.gsproject.data.db.entities.ApodEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.SimpleDateFormat
import java.util.*

object Converters {

    private const val DATE_FORMAT = "yyyy-MM-dd"

    @TypeConverter
    @JvmStatic
    fun convertDateToString(date: Date): String {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.format(date)
    }

    @TypeConverter
    @JvmStatic
    fun convertStringToDate(string: String): Date {
        val dateFormat = SimpleDateFormat(DATE_FORMAT, Locale.getDefault())
        return dateFormat.parse(string)!!
    }

    @TypeConverter
    fun apodToString(value: ApodEntity): String {
        val gson = Gson()
        val type = object : TypeToken<ApodEntity>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun stringToApod(value: String): ApodEntity {
        val gson = Gson()
        val type = object : TypeToken<ApodEntity>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun mapListToString(value: List<ApodEntity>): String {
        val gson = Gson()
        val type = object : TypeToken<List<ApodEntity>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun mapStringToList(value: String): List<ApodEntity> {
        val gson = Gson()
        val type = object : TypeToken<List<ApodEntity>>() {}.type
        return gson.fromJson(value, type)
    }
}
