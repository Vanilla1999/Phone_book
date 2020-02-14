package com.example.crime.database

import androidx.room.*

@Dao
abstract class CrimeDao {
    @Query("SELECT* FROM CRIME1")
    abstract fun getAllcrime():List<Crime1>

    @Query("SELECT* FROM CRIME1 where id =:id")
    abstract fun getCrime(id:Long):Crime1

    @Insert
   abstract fun insert(crime1: Crime1)

    @Update
    abstract fun update(crime1: Crime1)

    @Delete
    abstract fun delete(crime1: Crime1)
}