package com.example.crime

import java.util.*

open class Crime(
    var mId: UUID = UUID.randomUUID(),
    var mTitle: String? = null,
    var mDate: Date = Date(),
    var mSolved: Boolean = false,
    var mRequiresPolice:Boolean?=null
) {
}