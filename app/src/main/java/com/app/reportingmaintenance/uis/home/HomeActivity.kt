package com.app.reportingmaintenance.uis.home

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityHomeBinding
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.uis.charts.ChartsActivity
import com.app.reportingmaintenance.uis.disruption_typs.DisruptionTypesActivity
import com.app.reportingmaintenance.uis.faculties.FacultiesActivity
import com.app.reportingmaintenance.uis.reports.ReportsActivity
import com.app.reportingmaintenance.uis.students.StudentActivity
import com.app.reportingmaintenance.uis.technicians.TechnicActivity

class HomeActivity : AppCompatActivity() {
    private var binding: ActivityHomeBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        intitView()
    }
    private fun intitView() {
        binding!!.cardCharts.setOnClickListener(View.OnClickListener {
            var intent= Intent(this, ChartsActivity::class.java)
            startActivity(intent)
        })
        binding!!.cardReports.setOnClickListener(View.OnClickListener {
            var intent= Intent(this, ReportsActivity::class.java)
            startActivity(intent)
        })
        binding!!.cardTech.setOnClickListener(View.OnClickListener {
            var intent= Intent(this, TechnicActivity::class.java)
            startActivity(intent)
        })
        binding!!.cardDis.setOnClickListener(View.OnClickListener {
            var intent= Intent(this, DisruptionTypesActivity::class.java)
            startActivity(intent)
        })
        binding!!.cardFac.setOnClickListener(View.OnClickListener {
            var intent= Intent(this, FacultiesActivity::class.java)
            startActivity(intent)
        })
        binding!!.cardStu.setOnClickListener(View.OnClickListener {
            var intent= Intent(this, StudentActivity::class.java)
            startActivity(intent)
        })
    }
}