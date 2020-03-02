package com.example.crime

import android.content.Intent
import android.content.pm.ResolveInfo
import android.graphics.Bitmap
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.crime.database.Crime1
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.list_item_crime.view.*
import java.lang.ref.WeakReference

public class CrimeAdapter(
    private var crimes: List<Crime1>,
    private val fragmentListener: WeakReference<OnItemClickListener>,
    private val activityListener: WeakReference<OnItemClickListener>
) : RecyclerView.Adapter<CrimeAdapter.CrimeHolder>() {
    var mCrime = CrimeLab.instance
    private var compositeDisposable = CompositeDisposable()
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

        holder.itemView.crimeDate.text = crime.mDate.toString()


        holder.itemView.crime_solved.visibility = if (!crime.mSolved) View.GONE else View.VISIBLE
        compositeDisposable.add(
            mCrime.mDatabase.crimeDao.getAllcrime()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ list ->
                    val mPhotoFile = mCrime.getPhotofile(list[position])

                    if (!mPhotoFile.exists()) {
                        holder.itemView.crime_solved.setImageDrawable(null)
                    } else {
                        val bitmap: Bitmap =
                            PictureUtils.getScaledBitmap(mPhotoFile.path, 1000,1000)
                        holder.itemView.crime_solved.setImageBitmap(bitmap)
                    }


                }, { throwable -> Log.e("TAG", throwable.toString()) })
        )

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

    public interface OnItemClickListener {
        fun onClick(position: Int)
    }
}
