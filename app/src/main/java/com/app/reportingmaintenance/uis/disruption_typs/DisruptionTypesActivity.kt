package com.app.reportingmaintenance.uis.disruption_typs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityDisruptionTypsBinding
import com.app.reportingmaintenance.databinding.ActivityTechniciansBinding

class DisruptionTypesActivity : AppCompatActivity() {
    private var binding: ActivityDisruptionTypsBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disruption_typs)


    }

}