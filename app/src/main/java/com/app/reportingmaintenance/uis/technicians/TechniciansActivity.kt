package com.app.reportingmaintenance.uis.technicians

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityReportsBinding
import com.app.reportingmaintenance.databinding.ActivityTechniciansBinding
import com.app.reportingmaintenance.uis.addfaculty.AddFacultyActivity
import com.app.reportingmaintenance.uis.addtechnician.AddTechnicianActivity

class TechnicActivity : AppCompatActivity() {
    private var binding: ActivityTechniciansBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_technicians)

        intitView()
    }

    private fun intitView() {
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, AddTechnicianActivity::class.java)
            startActivity(intent)

        })
    }

}