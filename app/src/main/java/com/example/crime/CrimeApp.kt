package com.example.crime

import android.app.Application

class CrimeApp: Application() {
    override fun onCreate() {
        super.onCreate()
        CrimeLab.get().initCrimes()
    }
}