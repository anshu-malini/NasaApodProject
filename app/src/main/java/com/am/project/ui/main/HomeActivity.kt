package com.am.project.ui.main

import android.content.Context
import android.os.Bundle
import com.am.project.MainApplication
import com.am.project.R
import com.am.project.adapter.ViewPagerAdapter
import com.am.project.data.db.repository.ApodRepository
import com.am.project.databinding.ActHomeBinding
import com.am.project.ui.base.BaseActivity
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