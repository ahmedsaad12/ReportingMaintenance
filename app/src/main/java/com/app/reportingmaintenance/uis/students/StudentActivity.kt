package com.app.reportingmaintenance.uis.students

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.StudentAdapter
import com.app.reportingmaintenance.databinding.ActivityStudentsBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.ReportModel
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
        setUpToolbar(
            binding!!.toolbar,
            "Student",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
        userList = mutableListOf()

        studentAdapter=StudentAdapter(this,userList!!)

        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
binding!!.recview.layoutManager = LinearLayoutManager(this)
        binding!!.recview.adapter=studentAdapter
        binding!!.fab.setOnClickListener(View.OnClickListener {

            var intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)

        })
       // getData()
    }

    private fun getData() {
        val myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_USERS)
            .orderByChild("user_type").equalTo("user")
        userList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userList!!.clear()
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

    fun remove(userModel: UserModel) {
        val myMostViewedPostsQuery: Query =
            dRef!!.child(Tags.TABLE_REPORTS).orderByChild("student").equalTo(userModel.email!!.replaceAfter("@", "").replace("@", ""))
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val userModel = postSnapshot.getValue<ReportModel>()
                    dRef!!.child(Tags.TABLE_REPORTS).child(userModel!!.subject!!).removeValue()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
        dRef!!.child(Tags.TABLE_USERS).child(userModel.email!!.replaceAfter("@", "").replace("@", "")).removeValue().addOnSuccessListener {
            userList!!.remove(userModel)
            studentAdapter!!.notifyDataSetChanged()
        }


    }
}
