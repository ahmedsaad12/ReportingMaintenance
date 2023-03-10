package com.app.reportingmaintenance.uis.disruption_typs

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.FacultyDisributionAdapter
import com.app.reportingmaintenance.databinding.ActivityDisruptionTypsBinding
import com.app.reportingmaintenance.databinding.ActivityTechniciansBinding
import com.app.reportingmaintenance.model.FacultyDisributionModel
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.adddisribution.AddDisributionActivity
import com.app.reportingmaintenance.uis.signup.SignupActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class DisruptionTypesActivity : AppCompatActivity() {
    private var binding: ActivityDisruptionTypsBinding? = null
    private var dRef: DatabaseReference? = null
    private var disAdapter: FacultyDisributionAdapter? = null
    private var disList:MutableList<FacultyDisributionModel>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_disruption_typs)

        intitView()
    }

    private fun intitView() {
        disList = mutableListOf()

        disAdapter= FacultyDisributionAdapter(disList!!)

        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding!!.recview.layoutManager = GridLayoutManager(this,3)
        binding!!.recview.adapter=disAdapter
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, AddDisributionActivity::class.java)
            startActivity(intent)

        })
        getData()
    }

    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_DisruptionTypes)

        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val facultyDisributionModel = postSnapshot.getValue<FacultyDisributionModel>()

                    disList!!.add(facultyDisributionModel!!);

                }
                disAdapter!!.notifyDataSetChanged();
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}