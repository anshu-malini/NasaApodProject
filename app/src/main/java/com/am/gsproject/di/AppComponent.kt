package com.am.gsproject.di

import com.am.gsproject.data.db.repository.ApodRepository
import com.am.gsproject.ui.main.HomeActivity
import com.am.gsproject.ui.main.fragments.FavFragment
import com.am.gsproject.ui.main.fragments.HomeFragment
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, DaoModule::class])
interface AppComponent {
    fun inject(into: HomeActivity)
    fun inject(into: HomeFragment)
    fun inject(into: FavFragment)
    fun inject(into: ApodRepository)

}