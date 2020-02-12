package com.example.crime

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_crime_list.*
import java.lang.ref.WeakReference
import java.util.*

class CrimeListFragment : Fragment(R.layout.fragment_crime_list), CrimeAdapter.OnItemClickListener {
    private var mIsCheater: Boolean = false
    private val EXTRA_ANSWER_SHOWN2 = "KEK3"
    private val EXTRA_ANSWER_SHOWN = 0
    private var mSubtitleVisible: Boolean = true
    private var crimelab = CrimeLab.instance
    private lateinit var c: List<Crime>
    private lateinit var mAdapter: CrimeAdapter
    fun swapData(c: List<Crime>) {
        mAdapter =
            CrimeAdapter(c, WeakReference(this), WeakReference(activity as CrimeListActivity))
        return crimeRecyclerView.setAdapter(mAdapter)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        var crimes = crimelab.getCrimes()
        super.onActivityCreated(savedInstanceState)// this@CrimeListActivity
        if(savedInstanceState!=null) mSubtitleVisible=savedInstanceState.getBoolean(
            SAVED_SUBTITLE_VISIBLE)// передаем сохраненное значение
        crimeRecyclerView.layoutManager = LinearLayoutManager(activity)
        setHasOptionsMenu(true)
        mAdapter =
            CrimeAdapter(crimes, WeakReference(this), WeakReference(activity as CrimeListActivity))
        mAdapter.notifyDataSetChanged()

        return crimeRecyclerView.setAdapter(mAdapter)

    }


    override fun onClick(position: Int) {
        var intent = CrimePagerActivity.newIntent(context, position)
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
        mAdapter.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        var crimes = crimelab.getCrimes()
        swapData(crimes)
        mAdapter.notifyDataSetChanged()
        updateSubtitle()
        if(crimes.isEmpty()) activity?.textView?.text="Список пуст"
        else activity?.textView?.text= null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu, menu)
        val subtitleItem = menu.findItem(R.id.show_subtitle)
        if(mSubtitleVisible) subtitleItem.setTitle(R.string.hide_subtitle)
        else subtitleItem.setTitle(R.string.show_subtitle)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.new_crime -> {
                var crime = Crime()
                val kek = crimelab.addCrime(crime).mId
                var intent: Intent = CrimePagerActivity.newIntent(activity, kek)
                startActivity(intent)
                mAdapter.notifyDataSetChanged()
                return true
            }
            R.id.show_subtitle -> {
                mSubtitleVisible=!mSubtitleVisible
                activity?.invalidateOptionsMenu()// используется, чтобы сказать Android, что содержимое меню изменилось
                updateSubtitle()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    private fun updateSubtitle() {
        val crimeCount = crimelab.getCrimes().size
        var subtitle:String? = getString(R.string.subtitle_format, crimeCount)
        if(!mSubtitleVisible){
            subtitle= null
        }
        val activity = activity as AppCompatActivity// Добавляет строчку снизу с кол-во
        activity.supportActionBar?.subtitle = subtitle
    }

    override fun onSaveInstanceState(outState: Bundle) { // метод для сохранения состояния
        super.onSaveInstanceState(outState)
        outState.putBoolean(SAVED_SUBTITLE_VISIBLE,mSubtitleVisible)
    }
    companion object{
        val SAVED_SUBTITLE_VISIBLE = "subtitle"
    }
}



