package com.app.reportingmaintenance.uis.forgotpassword

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityForgotPasswordBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.ForgotPasswordModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class ForgotPasswordActivity : AppCompatActivity() {
    private var binding: ActivityForgotPasswordBinding? = null
    private var loginmodel: ForgotPasswordModel = ForgotPasswordModel()
    private var dRef: DatabaseReference? = null
    private var preferences: Preferences? = null
    private var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)

        intitView()

    }

    private fun intitView() {
        setUpToolbar(
            binding!!.toolbar,
            "Forgot Password",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        auth= FirebaseAuth.getInstance()
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding!!.model = loginmodel
        preferences = Preferences.newInstance()

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
                            Log.e("rrr", snapshot.value.toString())
                            for (postSnapshot in snapshot.children) {
                            val userModel = postSnapshot.getValue<UserModel>()

                               Log.e(";;llll", userModel!!.toMap().toString())
                            if (userModel!!.email == loginmodel.email ) {
                                requestChangePass(loginmodel.email)

                            }
                            else {
                                Toast.makeText(baseContext, "invaild user", Toast.LENGTH_LONG).show()
                            }
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
    }
    fun setUpToolbar(
        binding: ToolbarBinding,
        title: String?,
        background: Int,
        arrowTitleColor: Int
    ) {
        binding.lang = "en"
        binding.title = title
        binding.llBack.setColorFilter(ContextCompat.getColor(this, arrowTitleColor))
        binding.tvTitle.setTextColor(ContextCompat.getColor(this, arrowTitleColor))
        binding.toolbar.setBackgroundResource(background)
        binding.llBack.setOnClickListener { v -> finish() }
    }


    private fun requestChangePass( email:String) {
        FirebaseAuth.getInstance().sendPasswordResetEmail(email)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(
                        this, "Password Reset Email Sent!", Toast.LENGTH_LONG
                    ).show()
                    finish()
                } else {

                }
            }
    }


}