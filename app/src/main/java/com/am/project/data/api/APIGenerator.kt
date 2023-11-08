package com.am.project.data.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit

class APIGenerator constructor(
    private val retrofitBuilder: Retrofit.Builder, private val okHttpBuilder: OkHttpClient.Builder
) {
    fun <S> createService(serviceClass: Class<S>): S {
        okHttpBuilder.addInterceptor { chain ->
            val request = chain.request()
            val requestBuilder = request.newBuilder()
            requestBuilder.method(request.method, request.body)
            val response = chain.proceed(requestBuilder.build())
            response
        }
        return retrofitBuilder.client(okHttpBuilder.build()).build().create(serviceClass)
    }
}