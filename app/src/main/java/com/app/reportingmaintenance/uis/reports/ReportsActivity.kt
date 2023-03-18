package com.app.reportingmaintenance.uis.reports

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.app.reportingmaintenance.R
import com.app.reportingmaintenance.adapter.MyPagerAdapter
import com.app.reportingmaintenance.databinding.ActivityReportsBinding
import com.app.reportingmaintenance.preferences.Preferences
import com.app.reportingmaintenance.uis.addreport.AddReportsActivity
import com.app.reportingmaintenance.uis.reports.fragments.FragmentCurrentReport
import com.app.reportingmaintenance.uis.reports.fragments.FragmentPreviousReport
import com.app.reportingmaintenance.uis.signup.SignupActivity
import java.util.concurrent.ThreadLocalRandom.current

class ReportsActivity : AppCompatActivity() {
    private var preferences: Preferences? = null

    private var binding: ActivityReportsBinding? = null
    private var adapter: MyPagerAdapter? = null
    private var fragmentList: MutableList<Fragment>? = null
    private var titles: MutableList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
// Remember that you should never show the action bar if the
// status bar is hidden, so hide that too if necessary.
        binding = DataBindingUtil.setContentView(this, R.layout.activity_reports)

        intitView()
    }

    private fun intitView() {
        preferences = Preferences.newInstance()

        if (!preferences!!.getUserData(this).user_type.equals("user")) {
            binding!!.fab.visibility= VISIBLE
        }
        titles = mutableListOf()
        fragmentList = mutableListOf()

        titles!!.add("current")
        titles!!.add("previous")
        binding!!.tabs.setupWithViewPager(binding!!.viewPager)
        fragmentList!!.add(FragmentCurrentReport())
        fragmentList!!.add(FragmentPreviousReport())
        adapter = MyPagerAdapter(
            supportFragmentManager,
            PagerAdapter.POSITION_UNCHANGED,
            fragmentList!!,
            titles!!
        )
        binding!!.viewPager.setAdapter(adapter)
        binding!!.viewPager.setOffscreenPageLimit(fragmentList!!.size)
        binding!!.viewPager.addOnAdapterChangeListener { viewPager, oldAdapter, newAdapter ->
            if (binding!!.viewPager.currentItem == 1) {
                binding!!.fab.visibility = GONE
            } else {
                binding!!.fab.visibility = VISIBLE

            }
        }
        binding!!.fab.setOnClickListener {

            var intent = Intent(this, AddReportsActivity::class.java)
            startActivity(intent)

        }
    }
}