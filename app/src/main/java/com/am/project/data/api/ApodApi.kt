package com.am.project.data.api

import com.am.project.data.model.ApodResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApi {

    @GET("planetary/apod")
    suspend fun getApods(
        @Query("api_key") apiKey: String,
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
    ): List<ApodResponse>?
}