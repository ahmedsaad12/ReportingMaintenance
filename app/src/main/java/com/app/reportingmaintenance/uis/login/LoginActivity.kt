package com.app.reportingmaintenance.uis.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.app.reportingmaintenance.uis.signup.SignupActivity


class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
private var loginmodel:LoginModel = LoginModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        intitView()

    }

    private fun intitView() {
        binding!!.model=loginmodel
        binding!!.txtCreateAcount.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        })
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

        })
    }

}