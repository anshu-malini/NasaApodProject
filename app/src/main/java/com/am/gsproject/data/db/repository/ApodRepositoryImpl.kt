package com.am.gsproject.data.db.repository

import android.util.Log
import com.am.gsproject.data.api.ApodApi
import com.am.gsproject.data.db.daos.ApodDao
import com.am.gsproject.data.db.entities.ApodEntity
import com.am.gsproject.utils.LOG_TAG_NAME
import com.am.gsproject.utils.NETWORK_FAIL
import com.am.gsproject.utils.NetworkResult

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
                            Log.d(LOG_TAG_NAME, "data from net+ $response")

                            val apodList = response.map { item ->
                                ApodEntity(
                                    date = date,
                                    mediaType = item.mediaType,
                                    hdurl = item.hdurl,
                                    serviceVersion = item.serviceVersion,
                                    explanation = item.explanation,
                                    title = item.title,
                                    url = item.url,
                                    isFav = false
                                )
                            }
                            apodDao.insertApods(apodList)
                            return NetworkResult.success(apodList)
                        }
                    }
                }
                return NetworkResult.error(NETWORK_FAIL)
            }
        }
    }

    override fun setApodIsFav(apodId: Int, isFavValue: Boolean) {
        apodDao.setApodIsFav(apodId, isFavValue)
    }

    override fun getApodByIsFav(isFavValue: Boolean): List<ApodEntity> {
        return apodDao.getApodsByIsFav()
    }

}