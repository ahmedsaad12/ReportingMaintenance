package com.app.reportingmaintenance.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reportingmaintenance.databinding.FacultyDisributionRowBinding
import com.app.reportingmaintenance.model.FacultyDisributionModel

class FacultyDisributionAdapter(private val facultyDisributionList: MutableList<FacultyDisributionModel>) :
    RecyclerView.Adapter<FacultyDisributionViewHolder>() {
    var binding: FacultyDisributionRowBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyDisributionViewHolder {

        binding = FacultyDisributionRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FacultyDisributionViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: FacultyDisributionViewHolder, position: Int) {

        val facultyDisribution = facultyDisributionList[position]
        holder.bind(facultyDisribution)
    }

    override fun getItemCount(): Int = facultyDisributionList.size
}

class FacultyDisributionViewHolder(
    private val binding: FacultyDisributionRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(facultyDisributionModel: FacultyDisributionModel) {
        binding.model = facultyDisributionModel
    }
}