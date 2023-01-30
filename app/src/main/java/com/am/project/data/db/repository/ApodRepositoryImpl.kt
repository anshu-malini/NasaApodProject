package com.am.project.data.db.repository

import android.util.Log
import com.am.project.data.api.ApodApi
import com.am.project.data.db.daos.ApodDao
import com.am.project.data.db.entities.ApodEntity
import com.am.project.utils.*
import retrofit2.HttpException

class ApodRepositoryImpl(
    private var apodApi: ApodApi,
    private var apodDao: ApodDao
) : ApodRepository {

    override suspend fun getApods(
        apiKey: String,
        date: String,
        hasInternet: Boolean
    ): NetworkResult<List<ApodEntity>> {
        val dbDataValue = apodDao.getApodsByDate(date)
        if (dbDataValue.isNotEmpty()) {
            return NetworkResult.success(dbDataValue)
        } else {
            if (!hasInternet) {
                return NetworkResult.error(NETWORK_FAIL)
            } else {
                try {
                    val response = apodApi.getApods(apiKey, date, date)
                    if (response != null) {
                        Log.d(LOG_TAG_NAME, "data from internet+ $response")
                        val apodList = response.map { item ->
                            ApodEntity(
                                date = date,
                                mediaType = item.mediaType,
                                hdurl = item.hdurl,
                                serviceVersion = item.serviceVersion,
                                explanation = item.explanation,
                                title = item.title,
                                url = item.url,
                                isFav = "N"
                            )
                        }
                        val id = apodDao.insertApods(apodList)
                        apodList.onEach { apod ->
                            id.forEach { newlycreatedId ->
                                apod.apod_id = newlycreatedId
                            }
                        }
                        return NetworkResult.success(apodList)
                    }
                } catch (httpEx: HttpException) {
                    if (httpEx.code() == 400) {
                        return NetworkResult.error(CODE_400, null)
                    }
                }
            }
            return NetworkResult.error(GENERAL_ERROR)
        }
    }

    override suspend fun setApodIsFav(
        apodId: Long,
        isFavValue: String
    ): NetworkResult<List<ApodEntity>> {
        val apodList: List<ApodEntity>
        val result = apodDao.setApodIsFav(apodId, isFavValue)
        if (result == 1) {
            apodList = listOf(apodDao.getApodsById(apodId))
            return NetworkResult.success(apodList)
        }
        return NetworkResult.error(DB_UPDATE_FAILED_ERROR)
    }

    override suspend fun getApodByIsFav(): NetworkResult<List<ApodEntity>> {
        val dbDataValue = apodDao.getApodsByIsFav()
        return NetworkResult.success(dbDataValue)
    }

}