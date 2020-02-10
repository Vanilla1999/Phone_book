package com.example.crime

import android.graphics.Movie
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

// 1
class MoviesPagerAdapter(fragmentManager: FragmentManager, private val movies: ArrayList<Crime>) :
    FragmentStatePagerAdapter(fragmentManager) {

    // 2
    override fun getItem(position: Int): Fragment {
        return CrimeFragment.newInstance(position)
    }

    // 3
    override fun getCount(): Int {
        return movies.size
    }
}