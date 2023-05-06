package com.app.reportingmaintenance.uis.editstudent

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityEditStudentBinding
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.databinding.ActivitySignupBinding
import com.app.reportingmaintenance.model.EditStudentModel
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.SignupModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.google.firebase.database.ktx.getValue
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.util.*


class EditStudentActivity : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog
    lateinit var userModel: UserModel
    private var binding: ActivityEditStudentBinding? = null
    private var dRef: DatabaseReference? = null
    private var auth: FirebaseAuth? = null
    private var preferences: Preferences? = null
    private var isadd: Boolean = true
    private var loginmodel: EditStudentModel = EditStudentModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_student)
        intitView()

    }

    private fun intitView() {
        auth = FirebaseAuth.getInstance()
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        preferences = Preferences.newInstance()
        setData();
        binding!!.model = loginmodel
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            // Log.e("rrr", loginmodel.email.replaceAfter("@", "").replace("@", ""));
            dialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()
            setuser()


            //

        })

    }

    private fun setData() {
        userModel = preferences!!.getUserData(this)
        loginmodel.name = userModel.name!!
        loginmodel.email = userModel.email!!
        loginmodel.phone = userModel.phone!!
        loginmodel.studentNumber = userModel.studentNumber!!
    }

    private fun setuser() {

        val post = UserModel(
            userModel.id,
            loginmodel.studentNumber,
            loginmodel.email,
            "",
            loginmodel.name,
            loginmodel.phone,
            "user",
            ""
        )
        val postValues = post.toMap()
        dRef!!.child(Tags.TABLE_USERS)
            .child(userModel.id!!)
            .updateChildren(postValues).addOnSuccessListener {
                dialog.dismiss()
                if (preferences!!.getSession(this) != Tags.session_login) {
                    preferences!!.create_update_userData(this, post)
                    finish()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "invaild user", Toast.LENGTH_LONG).show()

            }
    }

    protected fun random(): String {
        val SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890"
        val salt = StringBuilder()
        val rnd = Random()
        while (salt.length < 18) {
            val index = (rnd.nextFloat() * SALTCHARS.length).toInt()
            salt.append(SALTCHARS[index])
        }
        return salt.toString()
    }
}