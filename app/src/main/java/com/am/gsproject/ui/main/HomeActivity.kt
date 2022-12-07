package com.am.gsproject.ui.main

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import com.am.gsproject.MainApplication
import com.am.gsproject.R
import com.am.gsproject.adapter.ViewPagerAdapter
import com.am.gsproject.data.db.repository.ApodRepository
import com.am.gsproject.databinding.ActHomeBinding
import com.am.gsproject.ui.base.BaseActivity
import com.am.gsproject.utils.LOG_TAG_NAME
import javax.inject.Inject

class HomeActivity : BaseActivity() {
    private lateinit var binding: ActHomeBinding
    override fun initDI() {
        (application as MainApplication).component.inject(this)
    }

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var repository: ApodRepository

    private val menuItems = listOf(
        R.id.menu_home,
        R.id.menu_fav
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpHomeContent.apply {
            adapter = ViewPagerAdapter(context, supportFragmentManager, lifecycle)
        }
        binding.vpHomeContent.setCurrentItem(0, false)
        binding.bottomNavigation.selectedItemId = menuItems.first()
        binding. bottomNavigation.setOnNavigationItemSelectedListener { item ->
            menuItems.forEachIndexed { index, menuId ->
                if (item.itemId == menuId) {
                    binding.vpHomeContent.currentItem = index
                    return@forEachIndexed
                }
            }
            true
        }
    }
}