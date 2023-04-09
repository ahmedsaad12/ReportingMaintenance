package com.app.reportingmaintenance.uis.faculties

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.DataAdapter
import com.app.reportingmaintenance.databinding.ActivityFacultiesBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.DataModel
import com.app.reportingmaintenance.model.ReportModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.addfaculty.AddFacultyActivity
import com.app.reportingmaintenance.uis.places.PlacesActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import java.util.*

class FacultiesActivity : AppCompatActivity() {
    private var binding: ActivityFacultiesBinding? = null
    private var dRef: DatabaseReference? = null
    private var facultyAdapter: DataAdapter? = null
    private var facultyList:MutableList<DataModel>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_faculties)
        intitView()
    }

    private fun intitView() {
        setUpToolbar(
            binding!!.toolbar,
            "Faculties",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        facultyList = mutableListOf()

        facultyAdapter=DataAdapter(facultyList!!,this)

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
facultyList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                facultyList!!.clear()
                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val dataModel = postSnapshot.getValue<DataModel>()

                    facultyList!!.add(dataModel!!);

                }
                facultyAdapter!!.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

    fun showPlaces(dataModel: DataModel) {
        var intent = Intent(this, PlacesActivity::class.java)
        intent.putExtra("faculty_id",dataModel.id)
        startActivity(intent)
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
    fun remove(data: DataModel) {
        val myMostViewedPostsQuery: Query =
            dRef!!.child(Tags.TABLE_REPORTS).orderByChild("idfac").equalTo(data.id)
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val userModel = postSnapshot.getValue<ReportModel>()
                    dRef!!.child(Tags.TABLE_REPORTS).child(userModel!!.id!!).removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        val myMostViewedPostsQuery1: Query =
            dRef!!.child(Tags.TABLE_Places).orderByChild("faculty_id").equalTo(data.id)
        myMostViewedPostsQuery1.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val dataModel = postSnapshot.getValue<DataModel>()
                    dRef!!.child(Tags.TABLE_Places).child(dataModel!!.id!!).removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        dRef!!.child(Tags.TABLE_Faculties).child(data.name!!).removeValue().addOnSuccessListener {
            facultyList!!.remove(data)
            facultyAdapter!!.notifyDataSetChanged()
        }


    }

}
