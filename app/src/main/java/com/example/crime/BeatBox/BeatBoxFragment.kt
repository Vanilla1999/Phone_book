package com.example.crime.BeatBox

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.crime.*
import com.example.crime.database.Crime1
import com.example.crime.databinding.FragmentBeatBoxBinding
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_beat_box.*
import kotlinx.android.synthetic.main.fragment_crime_list.*
import java.lang.ref.WeakReference

class BeatBoxFragment : Fragment(), BeatboxAdapter.OnItemClickListener {
    private lateinit var mAdapter: BeatboxAdapter
    private var crimelab = CrimeLab.instance
    private lateinit var list: List<Crime1>
    private var compositeDisposable = CompositeDisposable()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentBeatBoxBinding = inflate(
            inflater,
            R.layout.fragment_beat_box, container, false
        )
        binding.crimeRecyclerView2.layoutManager = GridLayoutManager(activity, 3)


        crimelab.CrimeLab(context)// потоки

        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    mAdapter =
                        BeatboxAdapter(
                            list,
                            WeakReference(this)
                        )

                    crimeRecyclerView2.adapter = mAdapter
                    mAdapter.notifyDataSetChanged()
                }, { throwable -> Log.e("TAG", throwable.toString()) })
        )
            return binding.root
    }

    override fun onResume() {
        super.onResume()
        compositeDisposable.add(
            crimelab.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    mAdapter =
                        BeatboxAdapter(
                            list,
                            WeakReference(this)
                        )
                    crimeRecyclerView2.adapter = mAdapter
                    mAdapter.notifyDataSetChanged()
                }, { throwable -> Log.e("TAG", throwable.toString()) }))
    }
    override fun onClick(position: Int) {
        var intent = CrimePagerActivity.newIntent(context, position + 1)
        startActivity(intent)
    }



}


