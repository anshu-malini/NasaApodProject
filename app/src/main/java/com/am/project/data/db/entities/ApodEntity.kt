package com.am.project.data.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "apod", indices = [Index(value = ["date"], unique = true)])
data class ApodEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "apod_id")
    var apod_id: Long = 0,

    @ColumnInfo(name = "date")
    val date: String? = null,

    @ColumnInfo(name = "media_type")
    val mediaType: String? = null,

    @ColumnInfo(name = "hdurl")
    val hdurl: String? = null,

    @ColumnInfo(name = "service_version")
    val serviceVersion: String? = null,

    @ColumnInfo(name = "explanation")
    val explanation: String? = null,

    @ColumnInfo(name = "title")
    val title: String? = null,

    @ColumnInfo(name = "url")
    val url: String? = null,

    @ColumnInfo(name = "isFav")
    val isFav: String = "N"
)
