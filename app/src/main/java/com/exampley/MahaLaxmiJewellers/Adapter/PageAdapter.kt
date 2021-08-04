package com.exampley.MahaLaxmiJewellers.Adapter

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import com.exampley.MahaLaxmiJewellers.ui.main.GoldFragment

class PageAdapter(fm: FragmentManager, behavior: Int, private val tabsNumber: Int) :
    FragmentPagerAdapter(fm, behavior) {
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                GoldFragment()
            }
            else -> getItem(position)
        }
    }

    override fun getCount(): Int {
        return tabsNumber
    }
}