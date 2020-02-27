package com.example.crime.BeatBox

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.crime.CrimeAdapter
import com.example.crime.R
import com.example.crime.database.Crime1
import kotlinx.android.synthetic.main.list_item_crime.view.*
import java.lang.ref.WeakReference

class BeatboxAdapter (
    private var crimes: List<Crime1>,
    private val fragmentListener: WeakReference<BeatboxAdapter .OnItemClickListener>

) : RecyclerView.Adapter<BeatboxAdapter.CrimeHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrimeHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return CrimeHolder(layoutInflater, parent)
    }

//    fun updateData(data: List<Crime>){
//        crimes=data
//    }

    override fun onBindViewHolder(holder: CrimeHolder, position: Int) {
        val crime = crimes[position]
        //updateData(crimes)




        holder.itemView.crime_solved.visibility= if(!crime.mSolved) View.GONE else View.VISIBLE



        holder.itemView.setOnClickListener {
            run {

                fragmentListener.get()?.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return crimes.size
    }


    class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.card_view_beat, parent, false))

    public interface OnItemClickListener{
        fun onClick(position: Int)
    }
}