package com.app.reportingmaintenance.uis.signup

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.databinding.ActivitySignupBinding
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.SignupModel


class SignupActivity : AppCompatActivity() {
    private var binding: ActivitySignupBinding? = null
private var loginmodel: SignupModel = SignupModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        actionBar?.hide()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        intitView()

    }

    private fun intitView() {
        binding!!.model=loginmodel;
    }

}