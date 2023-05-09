package com.app.reportingmaintenance.uis.login

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.forgotpassword.ForgotPasswordActivity
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.app.reportingmaintenance.uis.signup.SignupActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class LoginActivity : AppCompatActivity() {
    private var binding: ActivityLoginBinding? = null
    private var loginmodel: LoginModel = LoginModel()
    private var dRef: DatabaseReference? = null
    private var preferences: Preferences? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)

        intitView()

    }

    private fun intitView() {
        auth= FirebaseAuth.getInstance()

        preferences = Preferences.newInstance()
        if(preferences!!.getSession(this)==Tags.session_login){
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            finish()
        }
        else{
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding!!.model = loginmodel
        binding!!.txtCreateAcount.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        })
            binding!!.txtForgot.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)

        })
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
           // Log.e("rrr", loginmodel.email.replaceAfter("@", "").replace("@", ""));
            val dialog: ProgressDialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()
            dRef!!.child(Tags.TABLE_USERS)
                .orderByChild("email").equalTo(loginmodel.email).addValueEventListener(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {
                        dialog.dismiss()
                        if (snapshot.value != null) {
                            Log.e("rrr", snapshot.value.toString());
                            for (postSnapshot in snapshot.children) {
                            val userModel = postSnapshot.getValue<UserModel>()

                            //   Log.e(";;llll", userModel!!.toMap().toString())
                            if (userModel!!.email == loginmodel.email ) {

                                   // Log.e("llll", userModel!!.toMap().toString())
                                    auth!!.signInWithEmailAndPassword(
                                        loginmodel.email,
                                        loginmodel.password
                                    )
                                        .addOnCompleteListener {
                                        if (it.isSuccessful) {
                                        if (auth!!.currentUser!!.isEmailVerified) {

                                            preferences!!.create_update_userData(baseContext, userModel)

                                            val intent = Intent(baseContext, HomeActivity::class.java)
                                            startActivity(intent)
                                            finish()
                                        }
                                        else {
                                            Toast.makeText(
                                                baseContext,
                                                "please verfaiy your email",
                                                Toast.LENGTH_LONG
                                            ).show()

                                        }
                                        }
                                    }.addOnFailureListener( {
                                            Log.e("llll",it.toString())
                                        })


                            }
//                            else {
//                                Toast.makeText(baseContext, "invaild user", Toast.LENGTH_LONG).show()
//                            }
                            }
                        } else {
                            Toast.makeText(baseContext, "invaild user", Toast.LENGTH_LONG).show()

                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                        Toast.makeText(baseContext, "invaild user", Toast.LENGTH_LONG).show()

                    }

                })

            //

        })
    }}

}