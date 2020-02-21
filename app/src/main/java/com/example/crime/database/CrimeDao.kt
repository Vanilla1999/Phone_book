package com.example.crime.database

import androidx.room.*

import io.reactivex.Completable
import io.reactivex.Single
import java.util.*


@Dao
abstract class CrimeDao {
    @Query("SELECT* FROM CRIME1")
    abstract fun getAllcrime(): Single<List<Crime1>>

    @Query("SELECT* FROM CRIME1 where id =:id")
    abstract fun getCrime(id:Long):Single<Crime1>

    @Insert
   abstract fun insert(crime1: Crime1): Completable
    @Query("Update CRIME1 SET mDate=:date where id=:id")
    abstract fun update( date: Date,id:Int): Completable

    @Delete
    abstract fun delete(crime1: Crime1): Completable
}