package com.example.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crime.database.Crime1
import com.example.crime.database.crimeDatabase1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_crime_list.*


import java.lang.ref.WeakReference


class CrimeListFragment : Fragment(R.layout.fragment_crime_list), CrimeAdapter.OnItemClickListener {
    private var mIsCheater: Boolean = false
    private val EXTRA_ANSWER_SHOWN2 = "KEK3"
    private val EXTRA_ANSWER_SHOWN = 0
    private var mSubtitleVisible: Boolean = true
    private var crimelab = CrimeLab.instance
    lateinit var kek: crimeDatabase1
    private lateinit var c: List<Crime>
    private lateinit var mAdapter: CrimeAdapter

    private lateinit var list: List<Crime1>
    private var compositeDisposable = CompositeDisposable()

    fun swapData(c: List<Crime1>) {
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    mAdapter =
                        CrimeAdapter(list, WeakReference(this), WeakReference(activity as CrimeListActivity))
                    mAdapter.notifyDataSetChanged()
                    crimeRecyclerView.adapter = mAdapter
                })

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)// this@CrimeListActivity
        if (savedInstanceState != null) mSubtitleVisible = savedInstanceState.getBoolean(
            SAVED_SUBTITLE_VISIBLE
        )// передаем сохраненное значение
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        setHasOptionsMenu(true)
        crimelab.CrimeLab(context)// потоки

        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({ list ->
                    mAdapter =
                        CrimeAdapter(
                            list,
                            WeakReference(this),
                            WeakReference(activity as CrimeListActivity)
                        )
                    mAdapter.notifyDataSetChanged()
                    crimeRecyclerView.adapter = mAdapter
                }, {throwable -> Log.e("TAG", throwable.toString())})
        )

       // return crimeRecyclerView.setAdapter(mAdapter)

    }


    override fun onClick(position: Int) {
        var intent = CrimePagerActivity.newIntent(context, position+1)
        startActivity(intent)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) return
        if (requestCode == EXTRA_ANSWER_SHOWN) {
            if (data == null) return
            mIsCheater = CrimeActivity.wasAnswerShown(data)
        }
        if (requestCode == EXTRA_ANSWER_SHOWN) {
            if (data == null) return
            mIsCheater = CrimeActivity.wasAnswerShown(data)
        }
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { crimes ->
                    swapData(crimes)
                    mAdapter =
                        CrimeAdapter(
                            crimes,
                            WeakReference(this),
                            WeakReference(activity as CrimeListActivity)
                        )
                    mAdapter.notifyDataSetChanged()
                })

        updateSubtitle()
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { crimes ->
                    if (crimes.isEmpty() ) activity?.textView?.text = "Список пуст"
                    else activity?.textView?.text = null
                })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        val subtitleItem = menu.findItem(R.id.show_subtitle)
        if (mSubtitleVisible) subtitleItem.setTitle(R.string.hide_subtitle)
        else subtitleItem.setTitle(R.string.show_subtitle)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
         var crime1 = Crime1()
        when (item.itemId) {
            R.id.new_crime -> {
                crimelab.CrimeLab(context)
                compositeDisposable.add(
                    crimelab.mDatabase.crimeDao.insert(crime1)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            compositeDisposable.add(
                                crimelab.mDatabase.crimeDao.getAllcrime()
                                    .subscribeOn(Schedulers.io())
                                    .observeOn(AndroidSchedulers.mainThread())
                                    .subscribe { list ->

                                        val k = list.size
                                        var intent =
                                            CrimeActivity.newIntent(context,k)
                                        startActivity(intent)
                                        mAdapter =
                                            CrimeAdapter(
                                                list,
                                                WeakReference(this),
                                                WeakReference(activity as CrimeListActivity)
                                            )
                                        mAdapter.notifyDataSetChanged()
                                    }) })

                return true
            }
            R.id.show_subtitle -> {
                mSubtitleVisible = !mSubtitleVisible
                activity?.invalidateOptionsMenu()// используется, чтобы сказать Android, что содержимое меню изменилось
                updateSubtitle()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateSubtitle() {
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list ->
                    val crimeCount = list.size
                    var subtitle: String? = getString(R.string.subtitle_format, crimeCount)
                    if (!mSubtitleVisible) {
                        subtitle = null
                    }
                    val activity = activity as AppCompatActivity// Добавляет строчку снизу с кол-во
                    activity.supportActionBar?.subtitle = subtitle
                })

    }

    override fun onSaveInstanceState(outState: Bundle) { // метод для сохранения состояния
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE, mSubtitleVisible)
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
    companion object {
        val SAVED_SUBTITLE_VISIBLE = "subtitle"
    }
}



