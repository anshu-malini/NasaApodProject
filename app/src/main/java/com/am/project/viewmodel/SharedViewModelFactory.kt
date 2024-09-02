package com.am.project.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.am.project.MainApplication
import com.am.project.data.db.repository.ApodRepository

class SharedViewModelFactory constructor(
    private val repository: ApodRepository,
    private val app: MainApplication
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
            SharedViewModel(this.repository, app) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}