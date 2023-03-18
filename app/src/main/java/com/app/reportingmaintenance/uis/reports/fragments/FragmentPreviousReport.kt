package com.app.reportingmaintenance.uis.reports.fragments

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reportingmaintenance.adapter.ReportAdapter
import com.app.reportingmaintenance.databinding.FragmentReportsBinding
import com.app.reportingmaintenance.model.ReportModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.tags.Tags
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue

class FragmentPreviousReport : Fragment() {

    private lateinit var binding: FragmentReportsBinding
    private var dRef: DatabaseReference? = null
    private var reportAdapter: ReportAdapter? = null
    private var reportList:MutableList<ReportModel>?= null
    private var preferences: Preferences? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentReportsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        intitView()
    }

    private fun intitView() {
        preferences = Preferences.newInstance()
        reportList = mutableListOf()

        reportAdapter= ReportAdapter(reportList!!)

        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        binding.recview.layoutManager = LinearLayoutManager(activity)
        binding.recview.adapter=reportAdapter
        getData()
    }
    private fun getData() {

        val myMostViewedPostsQuery: Query
        if(preferences!!.getUserData(requireActivity()).user_type.equals("client")){
            myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_REPORTS)
                .orderByChild("student").equalTo(preferences!!.getUserData(requireActivity()).email!!.replaceAfter("@", "").replace("@", "")).orderByChild("status").equalTo("new")}
        else{
            myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_REPORTS).
            orderByChild("status").equalTo("new")
        }

        reportList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                reportList!!.clear()
                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val userModel = postSnapshot.getValue<ReportModel>()
                    dRef!!.child(Tags.TABLE_USERS).child(userModel!!.student!!).get().addOnSuccessListener {

                        userModel.student= it.getValue<UserModel>()!!.name
                        Log.e("ddd",userModel!!.student!!)
                        reportList!!.add(userModel!!)
                    }


                }
                reportAdapter!!.notifyDataSetChanged();
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

}