package com.am.gsproject.data.db.repository

import android.util.Log
import com.am.gsproject.data.api.ApodApi
import com.am.gsproject.data.db.daos.ApodDao
import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.utils.*

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
        Log.d(LOG_TAG_NAME, "date+ $date")
        Log.d(LOG_TAG_NAME, "data from db+ $dbDataValue")
        dbDataValue.run {
            if (this.isNotEmpty()) {
                return NetworkResult.success(dbDataValue)
            } else {
                if (hasInternet) {
                    val response = apodApi.getApods(apiKey, date, date)
                    response.run {
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
                    }
                }
                return NetworkResult.error(NETWORK_FAIL)
            }
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
        }
        return NetworkResult.error(DB_UPDATE_FAILED_ERROR)
    }

    override suspend fun getApodByIsFav(): NetworkResult<List<ApodEntity>> {
        val dbDataValue = apodDao.getApodsByIsFav()
        Log.d(LOG_TAG_NAME, "data from db+ $dbDataValue")
       return NetworkResult.success(dbDataValue)
//        return when (dbDataValue.isNotEmpty()) {
//            true -> NetworkResult.success(dbDataValue)
//            else -> NetworkResult.error(NO_DATA_FOUND)
//        }

    }

}