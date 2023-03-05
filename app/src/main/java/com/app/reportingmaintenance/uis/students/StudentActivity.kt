package com.app.reportingmaintenance.uis.students

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityStudentsBinding

class StudentActivity : AppCompatActivity() {
    private var binding: ActivityStudentsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_students)

        intitView()
    }

    private fun intitView() {
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        })
    }

}