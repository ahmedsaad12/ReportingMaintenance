package com.app.reportingmaintenance.uis.places

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.DataAdapter
import com.app.reportingmaintenance.databinding.ActivityPlacesBinding
import com.app.reportingmaintenance.model.DataModel
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.addplcae.AddPlaceActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class PlacesActivity : AppCompatActivity() {
    private var binding: ActivityPlacesBinding? = null
    private var dRef: DatabaseReference? = null
    private var placeAdapter: DataAdapter? = null
    private var placeList:MutableList<DataModel>?= null
    private var faculty_name=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_places)
        getDataFromIntent()
        intitView()
    }

    private fun getDataFromIntent() {
        var intent: Intent
        intent= getIntent()
        faculty_name= intent.getStringExtra("faculty_name")!!
    }

    private fun intitView() {
        placeList = mutableListOf()

        placeAdapter=DataAdapter(placeList!!,this)

        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding!!.recview.layoutManager = GridLayoutManager(this,3)
        binding!!.recview.adapter=placeAdapter
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, AddPlaceActivity::class.java)
            intent.putExtra("faculty_name",faculty_name)
            startActivity(intent)

        })
        getData()
    }

    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_Places)
            .orderByChild("faculty_name").equalTo(faculty_name);
        placeList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                placeList!!.clear()

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val dataModel = postSnapshot.getValue<DataModel>()

                    placeList!!.add(dataModel!!);

                }
                placeAdapter!!.notifyDataSetChanged();
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

}