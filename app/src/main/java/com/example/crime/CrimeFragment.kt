package com.example.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.fragment_crime.*
import kotlinx.android.synthetic.main.fragment_crime.view.*
import java.util.*
import androidx.core.widget.addTextChangedListener as addTextChangedListener1

class CrimeFragment : Fragment(R.layout.fragment_crime) {
    val DIALOG_DATE = "DialogDate"
    val REQUES_DATE=0
    var mCrime: Crime = Crime()
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mCrime = Crime()
        val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as Int
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
                mCrime.mTitle = s.toString()
            }
        })



        updateDate()
        crime_date.setOnClickListener {
            var manager: FragmentManager? = fragmentManager
            var dialog:DatePickerFragment = DatePickerFragment().newInstance(mCrime.mDate)
            dialog.setTargetFragment(this,REQUES_DATE)// Назначение целевого фрагмента, связываем фрагмент и диалоговое окно
            if (manager != null) {
                dialog.show(manager,DIALOG_DATE)
            }

        }
        crime_solved.setOnCheckedChangeListener { _, isChecked -> mCrime.mSolved = isChecked }
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {return}
        if (requestCode == REQUES_DATE) {
            if (data == null) return
            var date = data.getSerializableExtra("com.bignerdranch.android.criminalintent") as Date
            mCrime.mDate=date
            updateDate()
        }
    }

    private fun updateDate() {
        crime_date.text = mCrime.mDate.toString()
    }

    companion object {
        val ARG_CRIME_ID = "crime_id"
        fun newInstance(crimeId: Int): CrimeFragment {
            var args = Bundle()
            args.putSerializable(ARG_CRIME_ID, crimeId)
            var fragment = CrimeFragment()
            fragment.arguments = args
            return fragment
        }
    }
}