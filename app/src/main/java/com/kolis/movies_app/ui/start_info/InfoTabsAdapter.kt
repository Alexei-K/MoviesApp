package com.kolis.movies_app.ui.start_info

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import java.util.*

class InfoTabsAdapter(fm: FragmentManager, behavior: Int) :
    FragmentPagerAdapter(fm, behavior) {
    private val fragmentsMap =
        HashMap<Int, Fragment>()

    override fun getItem(position: Int): Fragment {
        return fragmentsMap[position]!!
    }

    override fun getCount(): Int {
        return fragmentsMap.size
    }

    fun addFragment(position: Int, fragment: Fragment) {
        fragmentsMap[position] = fragment
    }
}