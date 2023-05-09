package com.app.reportingmaintenance.uis.addadmin

import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddAdminBinding
import com.app.reportingmaintenance.databinding.ActivityAddtechnicianBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.*
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.*


class AddAdminActivity : AppCompatActivity() {
    private lateinit var dialog: ProgressDialog
    private var binding: ActivityAddAdminBinding? = null
private var addDataModel: AddAdminModel = AddAdminModel();
    private var dRef: DatabaseReference? = null
    private var preferences: Preferences? = null
    private var auth: FirebaseAuth? = null


    private var isadd:Boolean=true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_admin)
        intitView()

    }

    private fun intitView() {
        auth= FirebaseAuth.getInstance()

        setUpToolbar(
            binding!!.toolbar,
            "add Admin",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        preferences = Preferences.newInstance()


        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        addDataModel.context=this
        binding!!.model=addDataModel

        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            // Log.e("rrr", loginmodel.email.replaceAfter("@", "").replace("@", ""));
             dialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()

            dRef!!.child(Tags.TABLE_USERS)
                .orderByChild("email").equalTo(addDataModel.email).addValueEventListener  (object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                 // Log.e("rrr", snapshot.value.toString());
if(snapshot.value!=null){
    for (postSnapshot in snapshot.children) {
        val userModel = postSnapshot.getValue<UserModel>()
        Log.e(ContentValues.TAG, postSnapshot.value.toString())
        if (userModel!=null) {

            //   Log.e("rrr", it.value.toString());

            if (userModel!!.email == addDataModel.email &&isadd) {
                dialog.dismiss()
                Toast.makeText(baseContext, "user found", Toast.LENGTH_LONG).show()

            } else {
                if(isadd){
                    isadd=false
                    setuser()}
            }
        } else {
            if(isadd){
                isadd=false
                setuser()}

        }
    }
}else{
    if(isadd){
        isadd=false
    setuser()}
}

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


            //

        })

    }

    private fun setuser() {
        var id=random()

        val post = UserModel(id ,"",addDataModel.email,  "","","admin","")
       // addDataModel.email=""
        val postValues = post.toMap()
        dRef!!.child(Tags.TABLE_USERS)
            .child(id)
            .setValue(postValues).addOnSuccessListener {
                dialog.dismiss()
                Toast.makeText(this, "success", Toast.LENGTH_LONG).show()
             //   Log.e("ldldldl",addDataModel.password)

                auth!!.createUserWithEmailAndPassword(addDataModel.email, addDataModel.password).addOnCompleteListener {

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

//                preferences!!.create_update_userData(this,post)
//
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
//                auth!!.createUserWithEmailAndPassword(addDataModel.email, addDataModel.password).addOnCompleteListener {
//
//                    auth!!.currentUser!!.sendEmailVerification().addOnCompleteListener {
//                        if(it.isSuccessful){
//                           // Toast.makeText(this, "check your email", Toast.LENGTH_LONG).show()
//                            finish()
////                            if(preferences!!.getSession(this)!=Tags.session_login){
////                    preferences!!.create_update_userData(this,post)
//////
////                    val intent = Intent(this, HomeActivity::class.java)
////                    startActivity(intent)
////                        }
//                        }
//                    }
//
////
////                }
//
//                   // finish()
//                }


            }.addOnFailureListener {
                Toast.makeText(this, "invaild user", Toast.LENGTH_LONG).show()

            }
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
