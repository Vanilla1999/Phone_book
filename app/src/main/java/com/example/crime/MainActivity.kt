package com.example.crime

import androidx.fragment.app.Fragment
import java.util.*

class MainActivity : SingleFragmentActivity(){
val EXTRA_CRIME_ID="crime_id"
    override fun createFragment(): Fragment {
        var crimeId: UUID = intent.getSerializableExtra(EXTRA_CRIME_ID) as UUID
        return CrimeFragment.newInstance(crimeId )
    }


}
