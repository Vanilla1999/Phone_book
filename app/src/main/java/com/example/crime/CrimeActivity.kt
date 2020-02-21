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
        title1.text = intent.getIntExtra(EXTRA, 0).toString()
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


}
