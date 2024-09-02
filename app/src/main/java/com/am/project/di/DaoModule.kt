package com.am.project.di

import android.content.Context
import com.am.project.data.db.AppDatabase
import com.am.project.data.db.daos.ApodDao
import dagger.Module
import dagger.Provides

@Module
class DaoModule {
    @Provides
    fun providesApodDao(appDatabase: AppDatabase): ApodDao = appDatabase.apodDao()

    @Provides
    fun providesAppDatabase(context: Context): AppDatabase = AppDatabase.getDatabase(context)
}