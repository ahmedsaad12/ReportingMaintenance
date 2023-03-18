package com.app.reportingmaintenance.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reportingmaintenance.databinding.ReportRowBinding
import com.app.reportingmaintenance.model.ReportModel

class ReportAdapter(private val ReportList: MutableList<ReportModel>) :
    RecyclerView.Adapter<ReportViewHolder>() {
    var binding: ReportRowBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {

        binding = ReportRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReportViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {

        val Report = ReportList[position]
        holder.bind(Report)
    }

    override fun getItemCount(): Int = ReportList.size
}

class ReportViewHolder(
    private val binding: ReportRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(ReportModel: ReportModel) {
        binding.model = ReportModel
    }
}