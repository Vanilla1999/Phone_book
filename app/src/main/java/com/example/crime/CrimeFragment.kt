package com.example.crime

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import io.reactivex.Completable

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
    val REQUEST_PHOTO = 2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_crime, container, false)
    }

    override fun onResume() {
        super.onResume()
        crimeId = arguments!!.getInt(ARG_CRIME_ID)
        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    crime_solved.isChecked = list[crimeId].mSolved
                    crimeDate.text = list[crimeId].mDate.toString() // Надо будет доделать .
                    val mPhotoFile = mCrime.getPhotofile(list[crimeId])
                    if (!mPhotoFile.exists()) {
                        crime_photo.setImageDrawable(null)
                    } else {
                        val bitmap: Bitmap =
                            PictureUtils.getScaledBitmap(mPhotoFile.path, activity!!)
                        crime_photo.setImageBitmap(bitmap)
                    }

                }, { throwable -> Log.e("TAG", throwable.toString()) })
        )


    }

    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
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
                        .subscribe({ list ->
                            list[crimeId].mTitle = s.toString()

                        }, { throwable -> Log.e("TAG", throwable.toString()) })
                )

            }
        })

        crime_button.setOnClickListener {
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        val mPhotoFile = mCrime.getPhotofile(list[crimeId])
                        val captureImage = Intent(MediaStore.ACTION_IMAGE_CAPTURE)


                        val uri = FileProvider.getUriForFile(
                            context!!,
                            "com.bignerdranch.android.criminalintent.fileprovider",
                            mPhotoFile
                        )
                        captureImage.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                        val camerActivities: List<ResolveInfo> =
                            context!!.packageManager.queryIntentActivities(
                                captureImage,
                                PackageManager.MATCH_DEFAULT_ONLY
                            )
                        for (activity: ResolveInfo in camerActivities) {
                            context!!.grantUriPermission(
                                activity.activityInfo.packageName,
                                uri,
                                Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                            )
                        }
                        startActivityForResult(captureImage, REQUEST_PHOTO)
                        if (!mPhotoFile.exists()) {
                            crime_photo.setImageDrawable(null)
                        } else {
                            val bitmap: Bitmap =
                                PictureUtils.getScaledBitmap(mPhotoFile.path, activity!!)
                            crime_photo.setImageBitmap(bitmap)
                        }


                    }, { throwable -> Log.e("TAG", throwable.toString()) })
            )
        }
        crimeDate.setOnClickListener {
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        var manager: FragmentManager? = fragmentManager

                        var dialog: DatePickerFragment =
                            DatePickerFragment().newInstance(list[crimeId].mDate)
                        dialog.setTargetFragment(
                            this,
                            REQUES_DATE
                        )// Назначение целевого фрагмента, связываем фрагмент и диалоговое окно
                        if (manager != null) {
                            dialog.show(manager, DIALOG_DATE)
                        }

                    }, { throwable -> Log.e("TAG", throwable.toString()) })
            )

        }
        crime_solved.setOnCheckedChangeListener { _, isChecked ->
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        list[crimeId].mSolved = isChecked
                        compositeDisposable.add(
                            mCrime.mDatabase.crimeDao.update(isChecked, crimeId + 1)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                })


                    }, { throwable -> Log.e("TAG", throwable.toString()) })
            )
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
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
                        list[crimeId].mDate = date
                        compositeDisposable.add(
                            mCrime.mDatabase.crimeDao.update(date, crimeId + 1)
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe {
                                })

                    }


                    val mPhotoFile = mCrime.getPhotofile(list[crimeId])

                    if (requestCode == REQUEST_PHOTO) {
                        val uri = FileProvider.getUriForFile(
                            context!!,
                            "com.bignerdranch.android.criminalintent.fileprovider",
                            mPhotoFile
                        )
                        activity!!.revokeUriPermission(uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
                        if (!mPhotoFile.exists()) {
                            crime_photo.setImageDrawable(null)
                        } else {
                            val bitmap: Bitmap =
                                PictureUtils.getScaledBitmap(mPhotoFile.path, activity!!)
                            crime_photo.setImageBitmap(bitmap)
                        }
                    }
                }
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