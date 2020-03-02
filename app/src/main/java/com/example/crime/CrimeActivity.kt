package com.example.crime

import android.app.Activity
import android.app.FragmentManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity


import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crime.*
import java.util.*

class CrimeActivity : AppCompatActivity() {
    val DIALOG_DATE = "DialogDate"
    val REQUES_DATE = 0
    var mCrime = CrimeLab.instance
    private var compositeDisposable = CompositeDisposable()

    companion object {
        val EXTRA_ANSWER_SHOWN = "KEK2"
        val EXTRA_ANSWER_SHOWN1 = "KEK3"
        val EXTRA = "KEK"
        fun newIntent(con: Context?, crimeid: Int): Intent {
            val intent1 = Intent(con, CrimeActivity::class.java)
            intent1.putExtra(EXTRA, crimeid)
            return intent1
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN1, false)
        }

    }

    val EXTRA_ANSWER_SHOWN = "KEK2"
    val EXTRA = "KEK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_crime)
        var id = intent.getIntExtra(EXTRA, 0)
        crimeDate.setOnClickListener {
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        val fragmement = this.supportFragmentManager
                        var dialog: DatePickerFragment =
                            DatePickerFragment().newInstance(list[id-1].mDate)
                        if (fragmement != null) {
                            dialog.show(fragmement, DIALOG_DATE)
                        }

                        //  updateDate()
                    }, { throwable -> Log.e("TAG", throwable.toString()) })
            )
        }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var id = intent.getIntExtra(EXTRA, 0)
        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    super.onActivityResult(requestCode, resultCode, data)

                    lateinit var date: Date
                    if (resultCode != Activity.RESULT_OK) {
                        return@subscribe
                    }
                    if (requestCode == REQUES_DATE) {
                        if (data == null) return@subscribe
                        date =
                            data?.getSerializableExtra("com.bignerdranch.android.criminalintent") as Date

                        crimeDate.text = date.toString()
                        list[id-1].mDate = date
                        compositeDisposable.add(
                            mCrime.mDatabase.crimeDao.update(date, id )
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                })

                    }
                })
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }


}
