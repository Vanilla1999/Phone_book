package com.example.crime

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Image
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_crime.view.*
import java.lang.ref.WeakReference
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatter.ofPattern
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
        var formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("dd-MMMM-yyyy")
        } else {
            val pattern = "yyyy-MM-dd"
            val simpleDateFormat = SimpleDateFormat(pattern)
            holder.itemView.crime_date.text = simpleDateFormat.format(Date()).toString()
        }

        holder.itemView.crime_solved.visibility= if(!crime.mSolved) View.GONE else View.VISIBLE


        holder.itemView.crime_title.text = crime.mTitle
        holder.itemView.setOnClickListener {
            run {
                activityListener.get()?.onClick(position,true)
                fragmentListener.get()?.onClick(position,crime.mSolved)
            }
        }
    }

    override fun getItemCount(): Int {
        return crimes.size
    }


    class CrimeHolder(inflater: LayoutInflater, parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.list_item_crime, parent, false))

    public interface OnItemClickListener{
        fun onClick(position: Int,kekl:Boolean)
    }
}
