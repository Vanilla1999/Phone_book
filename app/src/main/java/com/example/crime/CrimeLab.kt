package com.example.crime

import android.content.Context
import androidx.room.Room
import com.example.crime.database.CrimeBaseHelper
import com.example.crime.database.crimeDatabase1

import java.util.*
import kotlin.collections.ArrayList

class CrimeLab  {
    private object HOLDER {
        val INSTANCE = CrimeLab()
    }

    companion object {
        val instance: CrimeLab by lazy { HOLDER.INSTANCE }
    }
     lateinit var mDatabase:crimeDatabase1
     lateinit var mComtext: Context
    fun CrimeLab(context:Context?){
         mComtext=context!!.applicationContext
         mDatabase= Room.databaseBuilder(mComtext,crimeDatabase1::class.java,"database").addMigrations().build()
    }
    private var crimes: List<Crime> = ArrayList()

     

    fun updateCrime(i1: Int, i: Boolean, c: List<Crime>): List<Crime> {
        for (k in 0..100)
            if (k == i1)
                c[k].mSolved = i
        return c
    }

    fun addCrime(c:Crime):Crime{
        c.mId= crimes.size +1
        c.mTitle = "Crime #${c.mId}"
        c.mSolved = (c.mId!! % 2 == 0)
        crimes=crimes+c
        return c
    }
    fun getCrimes(): List<Crime> {
        return crimes
    }

}
