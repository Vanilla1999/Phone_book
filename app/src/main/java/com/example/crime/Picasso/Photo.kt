package com.example.crime.Picasso

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Photo(val url: String) : Parcelable {

    companion object {
        fun getSunsetPhotos(): Array<Photo> {
            return arrayOf<Photo>(Photo("https://sun9-14.userapi.com/c824600/v824600656/27094/ABYLm9mSJPE.jpg"),
                Photo("https://goo.gl/Wqz4Ev"))
        }
    }
}