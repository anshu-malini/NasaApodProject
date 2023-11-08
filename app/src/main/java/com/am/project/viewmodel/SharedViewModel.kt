package com.am.project.viewmodel

import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.am.project.BuildConfig
import com.am.project.MainApplication
import com.am.project.data.db.entities.ApodEntity
import com.am.project.data.db.repository.ApodRepository
import com.am.project.utils.GENERAL_ERROR
import com.am.project.utils.LOG_TAG_NAME
import com.am.project.utils.NetworkResult
import com.am.project.utils.hasInternet
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SharedViewModel @Inject constructor(
    private var repository: ApodRepository,
    private var appContext: MainApplication
) : AndroidViewModel(appContext) {

    val apodData: MutableLiveData<NetworkResult<List<ApodEntity>>> = MutableLiveData()
    val favList: MutableLiveData<NetworkResult<List<ApodEntity>>> = MutableLiveData()

    fun getApods(date: String) {
        apodData.postValue(NetworkResult.loading())
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO){
                    apodData.postValue(repository.getApods(BuildConfig.API_KEY_TOKEN, date, appContext.hasInternet()))
                }
            } catch (ex: java.lang.Exception) {
                ex.localizedMessage?.let { Log.e(LOG_TAG_NAME, it) }
                apodData.postValue(NetworkResult.error(GENERAL_ERROR))
            }
        }
    }

    fun setApodFavStatus(apodId: Long, isFav: String, homeApodId: Long) {
        favList.postValue(NetworkResult.loading())
        viewModelScope.launch {
            try {
                val result = repository.setApodIsFav(apodId, isFav)
                getApodsFavList()
                if (apodId == homeApodId)
                    apodData.postValue(result)
            } catch (ex: java.lang.Exception) {
                ex.localizedMessage?.let { Log.e(LOG_TAG_NAME, it) }
                favList.postValue(NetworkResult.error(GENERAL_ERROR))
            }
        }
    }

    fun getApodsFavList() {
        favList.postValue(NetworkResult.loading())
        viewModelScope.launch {
            try {
                favList.postValue(repository.getApodByIsFav())
            } catch (ex: java.lang.Exception) {
                ex.localizedMessage?.let { Log.e(LOG_TAG_NAME, it) }
                favList.postValue(NetworkResult.error(GENERAL_ERROR))
            }
        }
    }
}
