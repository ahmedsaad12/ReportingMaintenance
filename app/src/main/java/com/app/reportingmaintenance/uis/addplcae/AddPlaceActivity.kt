package com.app.reportingmaintenance.uis.addplcae

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddfacultyBinding
import com.app.reportingmaintenance.databinding.ActivityAddplaceBinding
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.databinding.ActivitySignupBinding
import com.app.reportingmaintenance.model.*
import com.app.reportingmaintenance.tags.Tags
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddPlaceActivity : AppCompatActivity() {
    private var binding: ActivityAddplaceBinding? = null
    private var dRef: DatabaseReference? = null
    private var faculty_name=""

    private var addDataModel: AddDataModel = AddDataModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addplace)
        getDataFromIntent()
        intitView()
    }

    private fun getDataFromIntent() {
        var intent: Intent
        intent= getIntent()
        faculty_name= intent.getStringExtra("faculty_name")!!
    }
    private fun intitView() {
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)

        binding!!.model=addDataModel
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            val post = PlaceModel(addDataModel.name,faculty_name)
            val postValues = post.toMap()
            dRef!!.child(Tags.TABLE_Places).child(addDataModel.name).setValue(postValues).addOnFailureListener({}).addOnSuccessListener {
                finish()
            }
        })
    }

}