package com.example.crime

import java.util.*
import kotlin.collections.ArrayList

class CrimeLab {

    private var crimes: List<Crime> = ArrayList()

    fun initCrimes(): List<Crime> {
        for (i in 0..100) {
            var crime = Crime()
            crime.mTitle = "Crime #$i"
            crime.mSolved = (i % 2 == 0)
            crimes = crimes + crime
        }
        return crimes
    }
 fun updateCrime(i1:Int, i:Boolean,c:List<Crime>): List<Crime> {
     for(k in 0..100)
         if (k==i1)
      c[k].mSolved=i
     return c
 }
    fun getCrimes1():List<Crime>{
       return crimes
    }




    companion object {
        fun get(): CrimeLab = CrimeLab()
    }

}
