package com.app.reportingmaintenance.uis.addfaculty

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityAddfacultyBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.*
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.map.MapActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.*


class AddFacultyActivity : AppCompatActivity() {
    private var binding: ActivityAddfacultyBinding? = null
    private var dRef: DatabaseReference? = null
    private var launcher: ActivityResultLauncher<Intent>? = null
    private var addDataModel: AddDataModel = AddDataModel();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_addfaculty)
        intitView()

    }

    private fun intitView() {
        launcher =
            this.registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if ( result.getResultCode() === RESULT_OK && result.getData() != null) {
                    val location =
                        result.getData()!!.getSerializableExtra("location") as SelectedLocation
                    addDataModel.address= location.getAddress()!!
                    addDataModel.latlng=location.getLat() .toString()+ ","+location.getLng().toString()
                    binding!!.setModel(addDataModel)
                    Log.e("sda", "asda")
                }

            }

        setUpToolbar(
            binding!!.toolbar,
            "add Faculty",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)

        binding!!.model=addDataModel
        binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            var id=random()
            val post = DataModel(id,addDataModel.name,addDataModel.address,addDataModel.latlng)
            val dialog: ProgressDialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()
            dRef!!.child(Tags.TABLE_Faculties).child(id).setValue(post.toMap()).addOnFailureListener({}).addOnSuccessListener {
               dialog.dismiss()
                finish()
            }
        })
        binding!!.cardAddress.setOnClickListener { view -> navigateToMapActivity() }

    }

    private fun navigateToMapActivity() {
        val intent = Intent(this, MapActivity::class.java)
        launcher!!.launch(intent)
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
