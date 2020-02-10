package com.example.crime

import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.activity_main.*

class CrimeListActivity :SingleFragmentActivity(), CrimeAdapter.OnItemClickListener{
    override fun onClick(position: Int,kekl:Boolean) {
        if (textView.isVisible) textView.visibility = View.GONE else textView.visibility = View.VISIBLE
    }
    override fun createFragment(): Fragment {
        return CrimeListFragment()
    }

}