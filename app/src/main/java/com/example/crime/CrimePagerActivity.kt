package com.example.crime

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentStatePagerAdapter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_crime_pager.*

private var compositeDisposable = CompositeDisposable()

@Suppress("DEPRECATION")
// Засунуть в ФРАГМЕНТ
class CrimePagerActivity : AppCompatActivity(R.layout.activity_crime_pager) {

    private lateinit var mCrimes: List<Crime>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val crimelab = CrimeLab.instance
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ crimes ->
                    var crimeId =intent.getIntExtra("amount",0)
                    val fragment = supportFragmentManager
                    pager.adapter = object : FragmentStatePagerAdapter(fragment) {
                        override fun getCount(): Int {
                            return crimes.size
                        }

                        override fun getItem(position: Int): Fragment {

                            return CrimeFragment.newInstance(position)
                        }
                    }

                    for (i in 0..crimes.size) {
                        if (crimes[i].id == crimeId) {
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