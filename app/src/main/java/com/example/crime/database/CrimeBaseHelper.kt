package com.example.crime.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.crime.database.CrimeDbSchema.Companion.CrimeTable.Companion

class CrimeBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION) {

    companion object {
        val VERSION = 1
        val DATABASE_NAME = "crimeBase.db"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "create table" + CrimeDbSchema.Companion.CrimeTable.NAME + "(" +
                    " _id integer primary key autoincrement, " +
                    CrimeDbSchema.Companion.CrimeTable.Cols.TITLE + ", " +
                    CrimeDbSchema.Companion.CrimeTable.Cols.DATE + ", " +
                    CrimeDbSchema.Companion.CrimeTable.Cols.SOLVED +
                    ")"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}