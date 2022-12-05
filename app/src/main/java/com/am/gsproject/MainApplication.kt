package com.am.gsproject

import android.app.Application
import com.am.gsproject.di.*


class MainApplication : Application() {
    companion object {
        lateinit var instance: MainApplication
    }

    val component: AppComponent by lazy {
        DaggerAppComponent.builder()
            .netModule(NetModule())
            .appModule(AppModule(applicationContext))
            .daoModule(DaoModule()).build()
    }
}