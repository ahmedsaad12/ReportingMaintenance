package com.app.reportingmaintenance.uis.faculties

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.FacultyDisributionAdapter
import com.app.reportingmaintenance.adapter.StudentAdapter
import com.app.reportingmaintenance.databinding.ActivityFacultiesBinding
import com.app.reportingmaintenance.databinding.ActivityTechniciansBinding
import com.app.reportingmaintenance.model.FacultyDisributionModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.adddisribution.AddDisributionActivity
import com.app.reportingmaintenance.uis.addfaculty.AddFacultyActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class FacultiesActivity : AppCompatActivity() {
    private var binding: ActivityFacultiesBinding? = null
    private var dRef: DatabaseReference? = null
    private var facultyAdapter: FacultyDisributionAdapter? = null
    private var facultyList:MutableList<FacultyDisributionModel>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faculties)
        intitView()
    }

    private fun intitView() {
        facultyList = mutableListOf()

        facultyAdapter=FacultyDisributionAdapter(facultyList!!)

        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding!!.recview.layoutManager = GridLayoutManager(this,3)
        binding!!.recview.adapter=facultyAdapter
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, AddFacultyActivity::class.java)
            startActivity(intent)

        })
        getData()
    }

    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_Faculties)

        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val facultyDisributionModel = postSnapshot.getValue<FacultyDisributionModel>()

                    facultyList!!.add(facultyDisributionModel!!);

                }
                facultyAdapter!!.notifyDataSetChanged();
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

}