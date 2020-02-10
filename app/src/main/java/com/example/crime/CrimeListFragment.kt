package com.example.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_crime_list.*
import java.lang.ref.WeakReference
import java.util.*

class CrimeListFragment : Fragment(R.layout.fragment_crime_list), CrimeAdapter.OnItemClickListener {
    private var  mIsCheater:Boolean= false
    private val EXTRA_ANSWER_SHOWN2 = "KEK3"
    private val EXTRA_ANSWER_SHOWN = 0
    private val crimelab = CrimeLab.get()
    private var crimes = crimelab.initCrimes()
    private lateinit var  c:List<Crime>
   private lateinit var mAdapter:CrimeAdapter
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)// this@CrimeListActivity
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        mAdapter =
            CrimeAdapter(crimes, WeakReference(this), WeakReference(activity as CrimeListActivity))
        mAdapter.notifyDataSetChanged()
        return crimeRecyclerView.setAdapter(mAdapter)
    }


    override fun onClick(position: Int, kekl: Boolean) {
        var intent = CrimePagerActivity.newIntent(context, position)
        startActivityForResult(intent, EXTRA_ANSWER_SHOWN)
         crimes=  crimelab.updateCrime(position,mIsCheater,crimes)
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
        mAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()

    }
}



