package com.example.crime

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.crime.database.Crime1
import io.reactivex.Completable

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_crime.*
import java.lang.ref.WeakReference
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
        setHasOptionsMenu(true)
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
                    crime_title2.setText(list[crimeId].mTitle)
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


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        lateinit var _new: String
        lateinit var _before: String
        lateinit var _old: String
        lateinit var _after: String
        var _ignore = false

        crime_title.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                _before = s.subSequence(0, start).toString();
                _old = s.subSequence(start, start + count).toString();
                _after = s.subSequence(start + count, s.length).toString();
            }


            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                _new = s.subSequence(start, start + count).toString()
                // тут я бред написал


            }
        })
        button3.setOnClickListener {
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        var i = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${list[crimeId].mTitle}"))
                        startActivity(i)
                    }, { throwable -> Log.e("TAG", throwable.toString()) })
            )
        }
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
        crime_photo.setOnClickListener{
            compositeDisposable.add(
                mCrime.mDatabase.crimeDao.getAllcrime()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ list ->
                        var manager: FragmentManager? = fragmentManager

                        var dialog: DialogImage =
                            DialogImage().newInstance(list[crimeId])
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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu2, menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var crime1 = Crime1()
        when (item.itemId) {
            R.id.new_crime -> {
                compositeDisposable.add(
                    mCrime.mDatabase.crimeDao.getAllcrime()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { list ->
                            list[crimeId].mTitle = crime_title2.text.toString()
                            compositeDisposable.add(
                                mCrime.mDatabase.crimeDao.update(list[crimeId])
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe {})
                            Toast.makeText(activity, R.string.correct_kek, Toast.LENGTH_SHORT)
                                .show()
                        })
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        compositeDisposable.dispose()
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