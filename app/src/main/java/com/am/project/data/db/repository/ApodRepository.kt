package com.am.project.data.db.repository

import com.am.project.data.db.entities.ApodEntity
import com.am.project.utils.NetworkResult

interface ApodRepository {
    suspend fun getApods(
        apiKey: String,
        date: String,
        hasInternet: Boolean
    ): NetworkResult<List<ApodEntity>>

    suspend fun setApodIsFav(apodId: Long, isFavValue: String): NetworkResult<List<ApodEntity>>

    suspend fun getApodByIsFav(): NetworkResult<List<ApodEntity>>
}