package com.example.crime.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import androidx.room.migration.Migration
import android.icu.lang.UCharacter.GraphemeClusterBreak.V



@Database(entities = [Crime1::class],version = 1)
@TypeConverters(DatabaseConverters::class)
abstract class crimeDatabase1 :RoomDatabase() {
   abstract val crimeDao:CrimeDao
   val MIGRATION_1_2: Migration = object : Migration(1, 2) {
      override fun migrate(database: SupportSQLiteDatabase) {
         // Поскольку мы не изменяли таблицу, здесь больше ничего не нужно делать.
      }
   }
}