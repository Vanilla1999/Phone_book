package com.example.crime

import android.content.res.Configuration
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class CrimeListActivity :SingleFragmentActivity(), CrimeAdapter.OnItemClickListener{
    private var b=2
    override fun onClick(position: Int) {
        if (textView.isVisible) textView.visibility = View.GONE else textView.visibility = View.VISIBLE
    }
    override fun createFragment(): Fragment {
        return CrimeListFragment()

    }
    // if
override fun getLayoutResId():Int{
return R.layout.activity_twopane
}

}