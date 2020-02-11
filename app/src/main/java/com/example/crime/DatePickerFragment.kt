package com.example.crime

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePickerFragment : AppCompatDialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar:Calendar= Calendar.getInstance()
        var date= arguments?.getSerializable(ARG_DATE)
        calendar.time = date as Date
        var year=calendar.get(Calendar.YEAR)
        var month=calendar.get(Calendar.MONTH)
        var day=calendar.get(Calendar.DAY_OF_MONTH)
        var v: View =LayoutInflater.from(activity).inflate(R.layout.dialog_date, null)
        var mDate=v.findViewById<DatePicker>(R.id.dialog_date)
        mDate.init(year,month,day,null)
        return AlertDialog.Builder(activity)
            .setView(v)
            .setTitle(R.string.date_picker_title)
            .setNegativeButton(android.R.string.cancel, DialogInterface.OnClickListener(){ _: DialogInterface, _: Int ->
                year=mDate.year
                month = mDate.month
                day = mDate.dayOfMonth
                date=GregorianCalendar(year,month,day).time
                sendResult(Activity.RESULT_OK, date as Date)
            })
            .create()
    }
     val EXTRA_DATE="com.bignerdranch.android.criminalintent"
    private fun sendResult(resultCode:Int,date:Date){
        if(targetFragment==null){return}
        val intent = Intent()
        intent.putExtra(EXTRA_DATE,date)
        targetFragment!!.onActivityResult(targetRequestCode,resultCode,intent)
    }

    private val ARG_DATE = "date"
    fun newInstance(date: Date): DatePickerFragment {
        var args: Bundle = Bundle()
        args.putSerializable(ARG_DATE, date)
        var fragment: DatePickerFragment = DatePickerFragment()
        fragment.arguments = args
        return fragment
    }
}