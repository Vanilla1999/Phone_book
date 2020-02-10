package com.example.crime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

abstract   class SingleFragmentActivity :AppCompatActivity() { // Нужен для того,чтобы постоянно не писать один и тот же код. будем эти методы у себя реализовывать. ХЗ вроде и так все было норм
    protected abstract fun createFragment():Fragment
    protected override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fm: FragmentManager = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fr)
        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fr, fragment).commit()

        }
    }
}