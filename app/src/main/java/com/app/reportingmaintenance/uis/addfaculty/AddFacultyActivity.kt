package com.app.reportingmaintenance.uis.addfaculty

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddfacultyBinding
import com.app.reportingmaintenance.databinding.ActivityLoginBinding
import com.app.reportingmaintenance.databinding.ActivitySignupBinding
import com.app.reportingmaintenance.model.AddDataModel
import com.app.reportingmaintenance.model.LoginModel
import com.app.reportingmaintenance.model.SignupModel
import com.app.reportingmaintenance.tags.Tags
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddFacultyActivity : AppCompatActivity() {
    private var binding: ActivityAddfacultyBinding? = null
    private var dRef: DatabaseReference? = null

    private var addDataModel: AddDataModel = AddDataModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addfaculty)
        intitView()

    }

    private fun intitView() {
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)

        binding!!.model=addDataModel
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            dRef!!.child(Tags.TABLE_Faculties).child(addDataModel.name).child("name").setValue(addDataModel.name).addOnFailureListener({}).addOnSuccessListener {
                finish()
            }
        })
    }

}