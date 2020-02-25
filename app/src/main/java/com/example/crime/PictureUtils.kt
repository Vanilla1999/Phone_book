package com.example.crime

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Point

class PictureUtils {
    companion object {
        fun getScaledBitmap(path: String, detWidth: Int, destHeight: Int): Bitmap {
            var options: BitmapFactory.Options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(path, options)
            var srcWidth: Int = options.outWidth
            var srcHeight: Int = options.outHeight
            // вычисление степени масштабирования
            var inSamplesize: Int = 1
            if (srcHeight > destHeight || srcWidth > detWidth) {
                val heightScale = srcHeight / destHeight
                val widthScale = srcWidth / detWidth
                inSamplesize = Math.round(
                    if (heightScale > widthScale)
                        heightScale.toDouble()
                    else
                        widthScale.toDouble()
                ).toInt()
            }
            options = BitmapFactory.Options()
            options.inSampleSize=inSamplesize
            return BitmapFactory.decodeFile(path,options)
        }
        fun getScaledBitmap(path: String,activity:Activity):Bitmap{
            var size=Point()
            activity.windowManager.defaultDisplay.getSize(size)
            return getScaledBitmap(path,size.x,size.y)
        }
    }
}