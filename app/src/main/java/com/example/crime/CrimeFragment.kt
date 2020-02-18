package com.example.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crime.*
import java.util.*

class CrimeFragment : Fragment(R.layout.fragment_crime) {
    val DIALOG_DATE = "DialogDate"
    val REQUES_DATE = 0
    var mCrime = CrimeLab.instance
    private var compositeDisposable = CompositeDisposable()


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        crime_title.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                compositeDisposable.add(
                    mCrime.mDatabase.crimeDao.getAllcrime()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe ({ list ->
                            val crimeId = arguments!!.getInt(ARG_CRIME_ID) // Возвращает null почему-то
                            list[crimeId].mTitle = s.toString()

                        }, {throwable -> Log.e("TAG", throwable.toString())})
                )

            }
        })



        updateDate()
        crime_date.setOnClickListener {
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ list ->
                        var manager: FragmentManager? = fragmentManager
                        val crimeId = arguments!!.getInt(ARG_CRIME_ID) // Возвращает null почему-то
                        var dialog: DatePickerFragment = DatePickerFragment().newInstance(list[crimeId].mDate)
                        dialog.setTargetFragment(
                            this,
                            REQUES_DATE
                        )// Назначение целевого фрагмента, связываем фрагмент и диалоговое окно
                        if (manager != null) {
                            dialog.show(manager, DIALOG_DATE)
                        }

                    }, {throwable -> Log.e("TAG", throwable.toString())})
            )


        }
        crime_solved.setOnCheckedChangeListener { _, isChecked ->
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ list ->
                        val crimeId = arguments!!.getInt(ARG_CRIME_ID) // Возвращает null почему-то
                        list[crimeId].mSolved = isChecked
                    }, {throwable -> Log.e("TAG", throwable.toString())})
            )
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ list ->
                    super.onActivityResult(requestCode, resultCode, data)
                    if (resultCode != Activity.RESULT_OK) {
                        return@subscribe
                    }
                    if (requestCode == REQUES_DATE) {
                        if (data == null) return@subscribe
                        var date = data?.getSerializableExtra("com.bignerdranch.android.criminalintent") as Date
                        val crimeId = arguments!!.getInt(ARG_CRIME_ID) // Возвращает null почему-то
                        list[crimeId].mDate = date
                        updateDate()
                    }
                }, {throwable -> Log.e("TAG", throwable.toString())})
        )

    }

    private fun updateDate() {
        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ list ->
                    val crimeId = arguments!!.getInt(ARG_CRIME_ID) // Возвращает null почему-то
                    crime_date.text = list[crimeId].mDate.toString()

                }, {throwable -> Log.e("TAG", throwable.toString())})
        )

    }

    companion object {
        val ARG_CRIME_ID = "crime_id"
        fun newInstance(crimeId: Int): CrimeFragment {
            var args = Bundle()
            args.apply {
                putInt(ARG_CRIME_ID, crimeId)
            }

            var fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}