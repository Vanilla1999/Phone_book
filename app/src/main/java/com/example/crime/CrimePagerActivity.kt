package com.example.crime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.activity_crime_pager.*
import java.util.*

@Suppress("DEPRECATION")
class CrimePagerActivity : AppCompatActivity() {
    private val crimelab = CrimeLab.get()
    private lateinit var mCrimes: List<Crime>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crime_pager)
        var crimId = intent.getSerializableExtra(EXTRA_CRIME_ID) as Int
        mCrimes = crimelab.getCrimes1()
        val fragment = supportFragmentManager
        pager.adapter = object : FragmentStatePagerAdapter(fragment) {
            override fun getCount(): Int {
               return mCrimes.size
            }

            override fun getItem(position: Int): Fragment {
                val crime = mCrimes[position]
                return CrimeFragment.newInstance(position)
            }
        }
    }

    companion object {
        val EXTRA_CRIME_ID = "crime_id"
        fun newIntent(packageContext: Context?, crimeId: Int): Intent {
            var intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

}