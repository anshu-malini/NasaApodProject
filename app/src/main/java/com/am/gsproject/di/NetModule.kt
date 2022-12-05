package com.am.gsproject.di

import android.util.Log
import com.am.gsproject.BuildConfig
import com.am.gsproject.data.api.APIGenerator
import com.am.gsproject.utils.LOG_TAG_NAME
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
class NetModule {
    @Provides
    fun providesOkHttpBuilder(): OkHttpClient.Builder {
        val okHttpBuilder = OkHttpClient.Builder()
        val logging = HttpLoggingInterceptor { message ->
            Log.d(LOG_TAG_NAME, message)
        }
        logging.level = HttpLoggingInterceptor.Level.BODY
        okHttpBuilder.addInterceptor(logging)
        okHttpBuilder.addInterceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
                .method(request.method, request.body)
            chain.proceed(requestBuilder.build())
        }
        okHttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okHttpBuilder.readTimeout(10, TimeUnit.SECONDS)
        okHttpBuilder.writeTimeout(30, TimeUnit.SECONDS)
        return okHttpBuilder
    }

    @Provides
    fun providesRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    @Provides
    fun providesApiGenerator(
        retrofitBuilder: Retrofit.Builder,
        okHttpBuilder: OkHttpClient.Builder
    ): APIGenerator = APIGenerator(retrofitBuilder, okHttpBuilder)

}