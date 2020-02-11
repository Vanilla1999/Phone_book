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
class CrimePagerActivity : AppCompatActivity(R.layout.activity_crime_pager) {

    private lateinit var mCrimes: List<Crime>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var crimId = intent.getIntExtra(EXTRA_CRIME_ID,0)
        val crimelab = CrimeLab.instance
        var crimes = crimelab.getCrimes()
        val fragment = supportFragmentManager
        pager.adapter = object : FragmentStatePagerAdapter(fragment) {
            override fun getCount(): Int {
                return crimes.size
            }

            override fun getItem(position: Int): Fragment {
                val crime = crimes[position]
                return CrimeFragment.newInstance(position)
            }
        }

        for ( i in 0..crimes.size) {
            if (crimes[i].mId == crimId) {
                pager.currentItem = i

                break
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