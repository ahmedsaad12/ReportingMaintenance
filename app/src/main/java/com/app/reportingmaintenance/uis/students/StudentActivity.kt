package com.app.reportingmaintenance.uis.students

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.StudentAdapter
import com.app.reportingmaintenance.databinding.ActivityStudentsBinding
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.tags.Tags
import com.app.reportingmaintenance.uis.signup.SignupActivity
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue


class StudentActivity : AppCompatActivity() {
    private var binding: ActivityStudentsBinding? = null
    private var dRef: DatabaseReference? = null
    private var studentAdapter: StudentAdapter? = null
    private var userList:MutableList<UserModel>?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_students)

        intitView()
    }

    private fun intitView() {
        userList = mutableListOf()

        studentAdapter=StudentAdapter(userList!!)

        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
binding!!.recview.layoutManager = LinearLayoutManager(this)
        binding!!.recview.adapter=studentAdapter
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        })
        getData()
    }

    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_USERS)
            .orderByChild("user_type").equalTo("user");
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {

                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(TAG, postSnapshot.value.toString())
                    val userModel = postSnapshot.getValue<UserModel>()

                    userList!!.add(userModel!!);

                }
                studentAdapter!!.notifyDataSetChanged();
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }

}