package com.example.crime

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.facebook.stetho.Stetho

abstract   class SingleFragmentActivity :AppCompatActivity() { // Нужен для того,чтобы постоянно не писать один и тот же код. будем эти методы у себя реализовывать. ХЗ вроде и так все было норм
    protected abstract fun createFragment():Fragment
    @LayoutRes
    open fun getLayoutResId():Int{
        return R.layout.activity_main
    }
    protected override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        Stetho.initializeWithDefaults(this)
        setContentView(getLayoutResId())
        val fm: FragmentManager = supportFragmentManager
        var fragment: Fragment? = fm.findFragmentById(R.id.fr)
        if (fragment == null) {
            fragment = createFragment()
            fm.beginTransaction().add(R.id.fr, fragment).commit()

        }
    }
}