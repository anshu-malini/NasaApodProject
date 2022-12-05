package com.am.gsproject.ui.base

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity(), BaseView {
    abstract fun initDI()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        initDI()
    }

    override fun showNetworkError() {
        TODO("Not yet implemented")
    }

    override fun showLoadingDialog() {
        TODO("Not yet implemented")
    }

    override fun hideLoadingDialog() {
        TODO("Not yet implemented")
    }
}