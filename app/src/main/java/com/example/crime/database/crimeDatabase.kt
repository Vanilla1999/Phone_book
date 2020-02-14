package com.example.crime.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Crime1::class],version = 1)
abstract class crimeDatabase1 :RoomDatabase() {
   abstract val crimeDao:CrimeDao
}