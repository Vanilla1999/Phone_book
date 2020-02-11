package com.example.crime

import android.content.Context
import java.util.*
import kotlin.collections.ArrayList

class CrimeLab  {
    private object HOLDER {
        val INSTANCE = CrimeLab()
    }

    companion object {
        val instance: CrimeLab by lazy { HOLDER.INSTANCE }
    }


    private var crimes: List<Crime> = ArrayList()

     init{
        for (i in 0..100) {
            var crime = Crime()
            crime.mId = i
            crime.mTitle = "Crime #$i"
            crime.mSolved = (i % 2 == 0)
            crimes = crimes + crime
        }
    }

    fun updateCrime(i1: Int, i: Boolean, c: List<Crime>): List<Crime> {
        for (k in 0..100)
            if (k == i1)
                c[k].mSolved = i
        return c
    }

    fun getCrimes(): List<Crime> {
        return crimes
    }

}
