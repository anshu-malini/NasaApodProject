package com.am.project.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.am.project.ui.main.fragments.FavFragment
import com.am.project.ui.main.fragments.HomeFragment

class ViewPagerAdapter(
    context: Context,
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    private val fragmentList = listOf(
        { HomeFragment.newInstance(context) },
        { FavFragment.newInstance(context) }
    )

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
        return fragmentList[position].invoke()
    }
}