package com.app.reportingmaintenance.uis.charts

import android.content.ContentValues
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.databinding.ActivityChartsBinding
import com.app.reportingmaintenance.databinding.ToolbarBinding
import com.app.reportingmaintenance.model.ReportModel
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.tags.Tags
import com.google.firebase.database.*
import com.google.firebase.database.ktx.getValue
import lecho.lib.hellocharts.model.PieChartData
import lecho.lib.hellocharts.model.SliceValue


class ChartsActivity : AppCompatActivity() {
    private var binding: ActivityChartsBinding? = null
    private var dRef: DatabaseReference? = null
    private var newlowreportList: MutableList<ReportModel>? = null
    private var newmediumreportList: MutableList<ReportModel>? = null
    private var newhighreportList: MutableList<ReportModel>? = null
    private var finishmediumreportList: MutableList<ReportModel>? = null
    private var finishlowreportList: MutableList<ReportModel>? = null
    private var finishthighreportList: MutableList<ReportModel>? = null

    //    private var reportList: MutableList<ReportModel>? = null
    // on below line we are creating
    // a variable for bar data
    var pieData: MutableList<SliceValue?>? = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_charts)

        intitView()

    }

    private fun intitView() {
        newlowreportList = mutableListOf()
        newmediumreportList = mutableListOf()
        newhighreportList = mutableListOf()
        finishlowreportList = mutableListOf()
        finishmediumreportList = mutableListOf()
        finishthighreportList = mutableListOf()


        dRef = FirebaseDatabase.getInstance().getReference(Tags.DATABASE_NAME)
        setUpToolbar(
            binding!!.toolbar,
            "Charts",
            R.color.white,
            R.color.black
        )
        binding!!.toolbar.llBack.setOnClickListener {
            finish()
        }
              // on below line we are initializing our bar data set
getData()


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

    private fun getData() {

        val myMostViewedPostsQuery: Query
        myMostViewedPostsQuery = dRef!!.child(Tags.TABLE_REPORTS)



        newlowreportList!!.clear()
        newmediumreportList!!.clear()
        newhighreportList!!.clear()
        finishlowreportList!!.clear()
        finishmediumreportList!!.clear()
        finishthighreportList!!.clear()
        myMostViewedPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                newlowreportList!!.clear()
                newmediumreportList!!.clear()
                newhighreportList!!.clear()
                finishlowreportList!!.clear()
                finishmediumreportList!!.clear()
                finishthighreportList!!.clear()
                for (postSnapshot in dataSnapshot.children) {
                    // TODO: handle the post
                    Log.e(ContentValues.TAG, postSnapshot.value.toString())
                    val userModel = postSnapshot.getValue<ReportModel>()
                    if (userModel!!.periority.equals("low")) {
                        if (userModel.status.equals("new")) {
                            newlowreportList!!.add(userModel)
                        } else {
                            finishlowreportList!!.add(userModel)

                        }
                    }
                    else if (userModel.periority.equals("medium")) {
                        if (userModel.status.equals("new")) {
                            newmediumreportList!!.add(userModel)
                        } else {
                            finishmediumreportList!!.add(userModel)

                        }
                    }
                    else {
                        if (userModel.status.equals("new")) {
                            newhighreportList!!.add(userModel)
                        } else {
                            finishthighreportList!!.add(userModel)

                        }
                    }






                }
                var size=newlowreportList!!.size+newmediumreportList!!.size+newhighreportList!!.size+finishlowreportList!!.size+finishmediumreportList!!.size+finishthighreportList!!.size
                pieData!!.add(SliceValue(newlowreportList!!.size.toFloat()/size, Color.RED).setLabel("new Low"))
                pieData!!.add(SliceValue(newmediumreportList!!.size.toFloat()/size, Color.BLUE).setLabel(" new medium"))
                pieData!!.add(SliceValue(newhighreportList!!.size.toFloat()/size, Color.GREEN).setLabel("new High"))
                pieData!!.add(SliceValue(finishlowreportList!!.size.toFloat()/size, Color.CYAN).setLabel("Finished Low"))
                pieData!!.add(SliceValue(finishmediumreportList!!.size.toFloat()/size, Color.BLACK).setLabel("Finished medium"))
                pieData!!.add(SliceValue(finishthighreportList!!.size.toFloat()/size, Color.MAGENTA).setLabel("Finished High"))
                val pieChartData = PieChartData(pieData)
                pieChartData.setHasLabels(true);

                binding!!.chart.pieChartData = pieChartData
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(ContentValues.TAG, "loadPost:onCancelled", databaseError.toException())
                // ...
            }
        })
    }
}