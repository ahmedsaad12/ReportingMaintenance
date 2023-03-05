package com.app.reportingmaintenance.uis.addtechnician

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddfacultyBinding
import com.app.reportingmaintenance.databinding.ActivityAddtechnicianBinding
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.databinding.ActivitySignupBinding
import com.app.reportingmaintenance.model.AddDataModel
import com.app.reportingmaintenance.model.AddTechnicianModel
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.SignupModel


class AddTechnicianActivity : AppCompatActivity() {
    private var binding: ActivityAddtechnicianBinding? = null
private var addDataModel: AddTechnicianModel = AddTechnicianModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addtechnician)
        intitView()

    }

    private fun intitView() {
        binding!!.model=addDataModel;
    }

}