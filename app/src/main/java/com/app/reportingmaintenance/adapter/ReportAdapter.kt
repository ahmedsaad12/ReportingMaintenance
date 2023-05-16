package com.app.reportingmaintenance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.app.reportingmaintenance.databinding.ReportRowBinding
import com.app.reportingmaintenance.model.ReportModel
import com.app.reportingmaintenance.uis.faculties.FacultiesActivity
import com.app.reportingmaintenance.uis.reports.fragments.FragmentCurrentReport
import com.squareup.picasso.Picasso

class ReportAdapter(
    private val fragment: Fragment,
    private val ReportList: MutableList<ReportModel>
) :
    RecyclerView.Adapter<ReportViewHolder>() {
    private var b: Boolean = false
    lateinit var binding: ReportRowBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {

        binding = ReportRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {

        val Report = ReportList[position]
if(Report.image!!.isNotEmpty()){
        Picasso.get().load(Report.image).fit().into(binding!!.image)}
        binding.btnConfirm.setOnClickListener {
            if (fragment is FragmentCurrentReport) {
                val fragmentCurrentReport = fragment as FragmentCurrentReport
                fragmentCurrentReport.changestatus(ReportList.get(holder.layoutPosition))

            }
        }
        binding!!.b = b;
        holder.bind(Report)
    }

    override fun getItemCount(): Int = ReportList.size
    fun setshow(b: Boolean) {
        this.b = b;
    }
}

class ReportViewHolder(
    private val binding: ReportRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ReportModel: ReportModel) {
        binding.model = ReportModel
    }
}