package com.example.crime

import android.content.Context
import androidx.room.Room
import com.example.crime.database.Crime1
import com.example.crime.database.crimeDatabase1
import java.io.File

class CrimeLab  {
    private object HOLDER {
        val INSTANCE = CrimeLab()
    }
// Он каким-то образом передает контекст при инициализации
    companion object {
        val instance: CrimeLab by lazy { HOLDER.INSTANCE }
    }
     lateinit var mDatabase:crimeDatabase1
     lateinit var mComtext: Context
    fun CrimeLab(context:Context?){
         mComtext=context!!.applicationContext
         mDatabase= Room.databaseBuilder(mComtext,crimeDatabase1::class.java,"database").addMigrations().build()
    }
fun getPhotofile(crime1: Crime1):File{
    val filesDir=mComtext.filesDir
    return File(filesDir,"IMG_" + crime1.id.toString() + ".jpg")
}



}
