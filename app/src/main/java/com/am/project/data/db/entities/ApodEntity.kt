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
    var date: String? = null,

    @ColumnInfo(name = "media_type")
    var mediaType: String? = null,

    @ColumnInfo(name = "hdurl")
    var hdurl: String? = null,

    @ColumnInfo(name = "service_version")
    var serviceVersion: String? = null,

    @ColumnInfo(name = "explanation")
    var explanation: String? = null,

    @ColumnInfo(name = "title")
    var title: String? = null,

    @ColumnInfo(name = "url")
    var url: String? = null,

    @ColumnInfo(name = "_isFav")
    var _isFav: String = "N"
)
