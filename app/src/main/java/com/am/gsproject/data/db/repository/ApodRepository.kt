package com.am.gsproject.data.db.repository

import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.utils.NetworkResult

interface ApodRepository {
    suspend fun getApods(
        apiKey: String,
        date: String,
        hasInternet: Boolean
    ): NetworkResult<List<ApodEntity>>

    fun setApodIsFav(apodId: Int, isFavValue: Boolean)
    fun getApodByIsFav(isFavValue: Boolean = true): List<ApodEntity>
}