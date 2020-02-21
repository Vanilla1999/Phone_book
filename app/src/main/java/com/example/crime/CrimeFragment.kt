package com.example.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crime.*
import java.util.*
import kotlin.properties.Delegates

class CrimeFragment : Fragment() {
    val DIALOG_DATE = "DialogDate"
    val REQUES_DATE = 0
    var mCrime = CrimeLab.instance
    private var compositeDisposable = CompositeDisposable()
   var crimeId by Delegates.notNull<Int>() // Возвращает null почему-то




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate( R.layout.fragment_crime, container, false)
    }

    override fun onStart() {
        super.onStart()

    }

    override fun onResume() {
        super.onResume()
        updateDate()
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
                            list[crimeId].mTitle = s.toString()

                        }, {throwable -> Log.e("TAG", throwable.toString())})
                )

            }
        })




        crimeDate.setOnClickListener {
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe ({ list ->
                        var manager: FragmentManager? = fragmentManager

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
                        list[crimeId].mSolved = isChecked
                    }, {throwable -> Log.e("TAG", throwable.toString())})
            )
            }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        lateinit var date:Date
                    if (resultCode != Activity.RESULT_OK) {
                        return
                    }
                    if (requestCode == REQUES_DATE) {
                        if (data == null) return
                        date = data?.getSerializableExtra("com.bignerdranch.android.criminalintent") as Date

                        crimeDate.text=date.toString()
                       
                    }
        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.update(date,crimeId+1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ()
        )


    }

    private fun updateDate() {
        crimeId= arguments!!.getInt(ARG_CRIME_ID)
        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ list ->

                    crimeDate.text = list[crimeId].mDate.toString()

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