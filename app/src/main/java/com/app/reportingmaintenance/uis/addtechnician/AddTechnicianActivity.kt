package com.app.reportingmaintenance.uis.addtechnician

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddtechnicianBinding
import com.app.reportingmaintenance.model.*
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.home.HomeActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class AddTechnicianActivity : AppCompatActivity() {
    private var binding: ActivityAddtechnicianBinding? = null
private var addDataModel: AddTechnicianModel = AddTechnicianModel();
    private var dRef: DatabaseReference? = null
  var disAdapter :ArrayAdapter<String>?=null
    private var preferences: Preferences? = null

    private var disList:MutableList<String>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addtechnician)
        intitView()

    }

    private fun intitView() {
        preferences = Preferences.newInstance()

        disList = mutableListOf()
     disAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, disList!!)
        binding!!.spType.adapter=disAdapter
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        addDataModel.context=this
        binding!!.model=addDataModel
        binding!!.spType.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {

                    addDataModel.id= disList!![i];

                binding!!.model=addDataModel
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
        getData()
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            // Log.e("rrr", loginmodel.email.replaceAfter("@", "").replace("@", ""));

            dRef!!.child(Tags.TABLE_USERS)
                .child(addDataModel.email.replaceAfter("@", "").replace("@", "")).get()
                .addOnSuccessListener {
                    if (it.value != null) {
                        // Log.e("rrr", it.value.toString());
                        val dataSnapshot = (it as DataSnapshot)
                        val userModel = dataSnapshot.getValue<UserModel>()
                        if (userModel!!.email == addDataModel.email ) {
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
        val post = UserModel("" ,addDataModel.email, addDataModel.password, addDataModel.name,"","tech")
        val postValues = post.toMap()
        dRef!!.child(Tags.TABLE_USERS)
            .child(addDataModel.email.replaceAfter("@", "").replace("@", ""))
            .setValue(postValues).addOnSuccessListener {
//                preferences!!.create_update_userData(this,post)
//
//                val intent = Intent(this, HomeActivity::class.java)
//                startActivity(intent)
                finish()
            }.addOnFailureListener {
                Toast.makeText(this, "invaild user", Toast.LENGTH_LONG).show()

            }
    }
    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_DisruptionTypes)
disList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                disList!!.clear()

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val dataModel = postSnapshot.getValue<DataModel>()

                    disList!!.add(dataModel!!.name!!);

                }
                disAdapter!!.notifyDataSetChanged()


            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

}