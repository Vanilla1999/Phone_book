package com.example.crime

import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_crime.view.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*

public class CrimeAdapter(
    private var crimes: List<Crime>,
    private val fragmentListener: WeakReference<OnItemClickListener>,
    private val activityListener: WeakReference<OnItemClickListener>) : RecyclerView.Adapter<CrimeAdapter.CrimeHolder>() {

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

            holder.itemView.crime_date.text = crime.mDate.toString()


        holder.itemView.crime_solved.visibility= if(!crime.mSolved) View.GONE else View.VISIBLE


        holder.itemView.crime_title.text = crime.mTitle
        holder.itemView.setOnClickListener {
            run {
                activityListener.get()?.onClick(position)
                fragmentListener.get()?.onClick(position)
            }
        }
    }

    override fun getItemCount(): Int {
        return crimes.size
    }


    class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false))

    public interface OnItemClickListener{
        fun onClick(position: Int)
    }
}
