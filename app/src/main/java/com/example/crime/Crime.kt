package com.example.crime

import java.util.*

open class Crime(
    var mId: Int? =null,// Поменять тип
    var mTitle: String? = null,
    var mDate: Date = Date(),
    var mSolved: Boolean = false,
    var mRequiresPolice:Boolean?=null
) {
}