package com.app.reportingmaintenance.uis.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.app.reportingmaintenance.uis.signup.SignupActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private var loginmodel: LoginModel = LoginModel()
    private var dRef: DatabaseReference? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        intitView()

    }

    private fun intitView() {
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding!!.model = loginmodel
        binding!!.txtCreateAcount.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        })
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            dRef!!.child(Tags.TABLE_USERS)
                .child(loginmodel.email.replaceAfter("@", "").replace("@", "")).get()
                .addOnSuccessListener {
                    val userModel: UserModel = it.getValue(UserModel::class.java)!!
                    if (userModel.email == loginmodel.email && userModel.password == loginmodel.password) {
                        val intent = Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "invaild user", Toast.LENGTH_LONG).show()
                    }
                }


            //

        })
    }

}