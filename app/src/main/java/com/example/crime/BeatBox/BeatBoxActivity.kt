package com.example.crime.BeatBox

import android.content.Context
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.example.crime.CrimeAdapter
import com.example.crime.CrimeListFragment
import com.example.crime.R
import com.example.crime.SingleFragmentActivity
import kotlinx.android.synthetic.main.activity_main.*

class BeatBoxActivity : SingleFragmentActivity(){
    private var b=2

    override fun createFragment(): Fragment {
        return BeatBoxFragment()

    }

    override fun getLayoutResId():Int{
        return R.layout.activity_beat_box
    }
    companion object {
        val EXTRA = "KEK"
        fun newIntent(con: Context?): Intent {
            return Intent(con, BeatBoxActivity::class.java)
        }
    }
}