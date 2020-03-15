package com.example.crime
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.example.crime.database.Crime1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crime.*
import kotlinx.android.synthetic.main.paintlayout.view.*

import java.io.File


import java.io.Serializable
import java.util.*

class DialogImage : DialogFragment() {
    var mCrime = CrimeLab.instance
    private var compositeDisposable = CompositeDisposable()

    override fun getTheme(): Int {
        return R.style.Theme_Paint_Dialog_FullScreen
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        var builder = AlertDialog.Builder(activity)
        var inflater: LayoutInflater = activity!!.layoutInflater
        val v: View = inflater.inflate(R.layout.paintlayout, null)
        builder.setView(v)
        var crime = arguments?.getSerializable(ARG_DATE)
        val mPhotoFile = mCrime.getPhotofile(crime as Crime1)
        if (!mPhotoFile.exists()) {
            v.paint.setImageDrawable(null)
        } else {

            BitmapFactory.decodeFile(mPhotoFile.path)?.let { bitmap ->

                v.paint.setImageBitmap(bitmap)
            }
        }
        builder.setNegativeButton(
            R.string.correct_kek,
            DialogInterface.OnClickListener() { DialogInterface, i: Int ->
            })
        return builder.create()
    }


    val EXTRA_DATE = "com.bignerdranch.android.criminalintent"
    private fun sendResult(resultCode: Int, date: Date) {
        if (targetFragment == null) {
            return
        }
        val intent = Intent()
        intent.putExtra(EXTRA_DATE, date)
        targetFragment!!.onActivityResult(targetRequestCode, resultCode, intent)
    }

    private val ARG_DATE = "image"
    fun newInstance(crime: Crime1): DialogImage {
        var args: Bundle = Bundle()
        args.putSerializable(ARG_DATE, crime)
        var fragment: DialogImage = DialogImage()
        fragment.arguments = args
        return fragment
    }
}