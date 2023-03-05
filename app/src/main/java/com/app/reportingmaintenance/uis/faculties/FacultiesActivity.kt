package com.app.reportingmaintenance.uis.faculties

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityFacultiesBinding
import com.app.reportingmaintenance.databinding.ActivityTechniciansBinding
import com.app.reportingmaintenance.uis.adddisribution.AddDisributionActivity
import com.app.reportingmaintenance.uis.addfaculty.AddFacultyActivity

class FacultiesActivity : AppCompatActivity() {
    private var binding: ActivityFacultiesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faculties)
        intitView()
    }

    private fun intitView() {
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, AddFacultyActivity::class.java)
            startActivity(intent)

        })
    }

}