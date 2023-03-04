package com.app.reportingmaintenance.uis.technicians

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityReportsBinding
import com.app.reportingmaintenance.databinding.ActivityTechniciansBinding

class TechnicActivity : AppCompatActivity() {
    private var binding: ActivityTechniciansBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_technicians)


    }

}