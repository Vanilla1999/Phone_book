package com.example.crime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.ViewPager
import com.example.crime.database.Crime1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_crime_pager.*
import java.lang.ref.WeakReference
import java.util.*

private var compositeDisposable = CompositeDisposable()

@Suppress("DEPRECATION")
class CrimePagerActivity : AppCompatActivity(R.layout.activity_crime_pager) {

    private lateinit var mCrimes: List<Crime>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var crimId = intent.getIntExtra(EXTRA_CRIME_ID, 0)
        val crimelab = CrimeLab.instance
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ crimes ->
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

                    for (i in 0..crimes.size) {
                       var lel=crimId
                        if (crimes[i].id == crimId) {
                            pager.currentItem = i

                            break
                        }
                    }
                }, { throwable -> Log.e("TAG", throwable.toString()) })
        )

    }

    companion object {
        val EXTRA_CRIME_ID = "crime_id"
        fun newIntent(packageContext: Context?, crimeId: Int?): Intent {
            var intent = Intent(packageContext, CrimePagerActivity::class.java)
            intent.putExtra(EXTRA_CRIME_ID, crimeId)
            return intent
        }
    }

}