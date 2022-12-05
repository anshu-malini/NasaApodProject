package com.am.gsproject.data.model

data class ApodData(
    var apod_id: Int = 0,
    var date: String?,
    var mediaType: String?,
    var hdurl: String?,
    var serviceVersion: String?,
    var explanation: String?,
    var title: String?,
    var url: String?,
    var isFav: Boolean = false,
)