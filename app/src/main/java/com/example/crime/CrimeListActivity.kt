package com.example.crime

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.multidex.MultiDex
import androidx.navigation.NavController
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

open class CrimeListActivity :SingleFragmentActivity(), CrimeAdapter.OnItemClickListener{
    private var b=2
    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        MultiDex.install(this)
    }
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
open fun navig():NavController{
    return Navigation.findNavController(this,R.id.nav_host_fragment_container)
}

}