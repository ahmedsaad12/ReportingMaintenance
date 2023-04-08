package com.app.reportingmaintenance.uis.adddisribution

import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.*
import com.app.reportingmaintenance.model.AddDataModel
import com.app.reportingmaintenance.share.Common
import com.app.reportingmaintenance.tags.Tags
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class AddDisributionActivity : AppCompatActivity() {
    private var binding: ActivityAdddisributionBinding? = null
private var addDataModel: AddDataModel = AddDataModel();
    private var dRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_adddisribution)
        intitView()

    }

    private fun intitView() {
        setUpToolbar(
            binding!!.toolbar,
            "Add Distribution Type",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)

        binding!!.model=addDataModel
      /*  binding!!.btnLogin.setOnClickListener(View.OnClickListener {
            val dialog: ProgressDialog = Common.createProgressDialog(
                this,
                "wait"
            )!!
            dialog.setCancelable(false)
            dialog.show()
            dRef!!.child(Tags.TABLE_DisruptionTypes).child(addDataModel.name).child("name").setValue(addDataModel.name).addOnFailureListener({}).addOnSuccessListener {
                dialog.dismiss()
                finish()
            }
        })*/

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

}
