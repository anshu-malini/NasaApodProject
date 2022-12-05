package com.am.gsproject.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.am.gsproject.data.db.repository.ApodRepository

class HomeViewModelFactory constructor(
    private val repository: ApodRepository,
    private val hasInternet: Boolean
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            HomeViewModel(this.repository, hasInternet) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}