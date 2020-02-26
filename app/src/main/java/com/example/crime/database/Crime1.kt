package com.example.crime.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "Crime1")
class Crime1 (
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
     var mTitle: String? = null,
     var mDate: Date = Date(),
     var mSolved: Boolean = false,
      var mRequiresPolice:Boolean?=null
)