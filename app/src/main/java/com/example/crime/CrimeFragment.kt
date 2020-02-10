package com.example.crime

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
    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var mCrime = Crime()
       val crimeId = arguments?.getSerializable(ARG_CRIME_ID) as Int
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        var mCrime: Crime = Crime()
        crime_title.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                mCrime.mTitle = s.toString()
            }
        })



        crime_date.text = mCrime.mDate.toString()
        crime_date.setOnClickListener {
            var dialog=DatePickerFragment()
            var manager: FragmentManager? =fragmentManager
            fragmentManager?.let { it1 -> dialog.show(it1,DIALOG_DATE) }
        }
        crime_solved.setOnCheckedChangeListener { _, isChecked -> mCrime.mSolved = isChecked }
    }
companion object {
    val ARG_CRIME_ID="crime_id"
     fun newInstance(crimeId: Int) : CrimeFragment{
       var args=Bundle()
         args.putSerializable(ARG_CRIME_ID,crimeId)
         var fragment=CrimeFragment()
         fragment.arguments = args
         return fragment
    }
}
}