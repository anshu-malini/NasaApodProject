package com.am.gsproject.di

import android.content.Context
import com.am.gsproject.BuildConfig
import com.am.gsproject.data.api.APIGenerator
import com.am.gsproject.data.api.ApodApi
import com.am.gsproject.data.db.AppDatabase
import com.am.gsproject.data.db.daos.ApodDao
import com.am.gsproject.data.db.repository.ApodRepository
import com.am.gsproject.data.db.repository.ApodRepositoryImpl
import com.am.gsproject.ui.main.fragments.FavFragment
import com.am.gsproject.ui.main.fragments.HomeFragment
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit

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
    fun provideHomeFragment(context: Context, repository: ApodRepository): HomeFragment =
        HomeFragment(context)

    @Provides
    fun provideFavFragment(context: Context, repository: ApodRepository): FavFragment =
        FavFragment(context)
}