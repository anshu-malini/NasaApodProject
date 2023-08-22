package com.am.project.di

import android.content.Context
import com.am.project.BuildConfig
import com.am.project.data.api.APIGenerator
import com.am.project.data.api.ApodApi
import com.am.project.data.db.daos.ApodDao
import com.am.project.data.db.repository.ApodRepository
import com.am.project.data.db.repository.ApodRepositoryImpl
import com.am.project.ui.main.fragments.FavFragment
import com.am.project.ui.main.fragments.HomeFragment
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val mContext: Context) {

    @Provides
    fun providesContext() = mContext

    @Provides
    fun providesApodRepository(apodApi: ApodApi, apodDao: ApodDao): ApodRepository =
        ApodRepositoryImpl(apodApi, apodDao)

    @Provides
    fun providesApodAPI(apiGenerator: APIGenerator): ApodApi =
        apiGenerator.createService(BuildConfig.BASE_URL, ApodApi::class.java)

    @Provides
    fun provideHomeFragment(context: Context): HomeFragment =
        HomeFragment(context)

    @Provides
    fun provideFavFragment(context: Context): FavFragment =
        FavFragment(context)
}