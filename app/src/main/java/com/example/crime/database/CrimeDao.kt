package com.example.crime.database

import androidx.room.*

import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.disposables.Disposable
import java.util.*


@Dao
abstract class CrimeDao {
    @Query("SELECT* FROM CRIME1")
    abstract fun getAllcrime(): Single<List<Crime1>>

    @Query("SELECT* FROM CRIME1 where id =:id")
    abstract fun getCrime(id:Long):Single<Crime1>

    @Insert
   abstract fun insert(crime1: Crime1):Completable
    @Query("UPDATE CRIME1 SET mSolved=:f where id=:id")
    abstract fun update( f:Boolean,id:Int):Completable
    @Query("UPDATE CRIME1 SET mDate=:f where id=:id")
    abstract fun update( f:Date,id:Int):Completable
    @Update
    abstract fun update(crime1: Crime1):Completable

    @Delete
    abstract fun delete(crime1: Crime1):Completable
}