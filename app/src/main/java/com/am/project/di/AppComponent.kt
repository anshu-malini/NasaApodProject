package com.am.project.di

import com.am.project.data.db.repository.ApodRepository
import com.am.project.ui.main.HomeActivity
import com.am.project.ui.main.fragments.FavFragment
import com.am.project.ui.main.fragments.HomeFragment
import dagger.Component

@Component(modules = [AppModule::class, NetModule::class, DaoModule::class])
interface AppComponent {
    fun inject(into: HomeActivity)
    fun inject(into: HomeFragment)
    fun inject(into: FavFragment)
    fun inject(into: ApodRepository)

}