package com.example.crime.database

import androidx.room.*

import io.reactivex.Completable
import io.reactivex.Single


@Dao
abstract class CrimeDao {
    @Query("SELECT* FROM CRIME1")
    abstract fun getAllcrime(): Single<List<Crime1>>

    @Query("SELECT* FROM CRIME1 where id =:id")
    abstract fun getCrime(id:Long):Single<Crime1>

    @Insert
   abstract fun insert(crime1: Crime1): Completable

    @Update
    abstract fun update(crime1: Crime1)

    @Delete
    abstract fun delete(crime1: Crime1): Completable
}