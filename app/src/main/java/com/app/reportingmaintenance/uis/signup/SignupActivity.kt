package com.app.reportingmaintenance.uis.signup

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
import com.app.reportingmaintenance.databinding.ActivitySignupBinding
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.SignupModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.getValue
import com.google.firebase.auth.FirebaseAuth


class SignupActivity : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog

    private var binding: ActivitySignupBinding? = null
    private var dRef: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var preferences: Preferences? = null
    private var loginmodel: SignupModel = SignupModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        intitView()

    }

    private fun intitView() {
        auth=FirebaseAuth.getInstance()
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        preferences = Preferences.newInstance()
        binding!!.model=loginmodel
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            // Log.e("rrr", loginmodel.email.replaceAfter("@", "").replace("@", ""));
            dialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()
            dRef!!.child(Tags.TABLE_USERS)
                .child(loginmodel.email.replaceAfter("@", "").replace("@", "")).get()
                .addOnSuccessListener {
                    if (it.value != null) {
                       // Log.e("rrr", it.value.toString());
                        val dataSnapshot = (it as DataSnapshot)
                        val userModel = dataSnapshot.getValue<UserModel>()
                        if (userModel!!.email == loginmodel.email ) {
                            dialog.dismiss()
                            Toast.makeText(this, "user found", Toast.LENGTH_LONG).show()

                        } else {
                            setuser()
                        }
                    } else {
                        setuser()

                    }
                }


            //

        })

    }

    private fun setuser() {
        val post = UserModel(loginmodel.studentNumber, loginmodel.email, loginmodel.password, loginmodel.name,loginmodel.phone,"user")
        val postValues = post.toMap()
        dRef!!.child(Tags.TABLE_USERS)
            .child(loginmodel.email.replaceAfter("@", "").replace("@", ""))
            .setValue(postValues).addOnSuccessListener {
                dialog.dismiss()
                auth!!.createUserWithEmailAndPassword(loginmodel.email, loginmodel.password).addOnCompleteListener {

                    auth!!.currentUser!!.sendEmailVerification().addOnCompleteListener {
                        if(it.isSuccessful){
                            Toast.makeText(this, "check your email", Toast.LENGTH_LONG).show()
                            finish()
//                            if(preferences!!.getSession(this)!=Tags.session_login){
//                    preferences!!.create_update_userData(this,post)
////
//                    val intent = Intent(this, HomeActivity::class.java)
//                    startActivity(intent)
//                        }
                    }
                }

//
//                }


            }
            }
                    .addOnFailureListener {
                Toast.makeText(this, "invaild user", Toast.LENGTH_LONG).show()

            }
    }

}