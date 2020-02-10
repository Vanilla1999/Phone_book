package com.example.crime

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_crime.*
import java.util.*

class CrimeActivity : AppCompatActivity() {
    companion object {
        val EXTRA_ANSWER_SHOWN = "KEK2"
        val EXTRA_ANSWER_SHOWN1 = "KEK3"
        val EXTRA = "KEK"
        fun newIntent(con: Context?, crimeid: Int, solve: Boolean): Intent {
            val intent1 = Intent(con, CrimeActivity::class.java)
            intent1.putExtra(EXTRA, crimeid)
            intent1.putExtra(EXTRA_ANSWER_SHOWN, solve)
            return intent1
        }

        fun wasAnswerShown(result: Intent): Boolean {
            return result.getBooleanExtra(EXTRA_ANSWER_SHOWN1, false)
        }

    }

    val EXTRA_ANSWER_SHOWN = "KEK2"
    val EXTRA = "KEK"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_crime)

        title1.text = intent.getIntExtra(EXTRA, 0).toString()
        crime_solved.isChecked = intent.getBooleanExtra(EXTRA_ANSWER_SHOWN, false)
        crime_date.setOnClickListener {
            fun setAnswerShownResult(ans: Boolean) {
                val data: Intent = Intent().putExtra(EXTRA_ANSWER_SHOWN1, ans)
                setResult(Activity.RESULT_OK, data)
                finish()
            }
            setAnswerShownResult(crime_solved.isChecked)
        }


    }


}
