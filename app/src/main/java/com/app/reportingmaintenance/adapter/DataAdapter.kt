package com.app.reportingmaintenance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reportingmaintenance.databinding.DataRowBinding
import com.app.reportingmaintenance.model.DataModel
import com.app.reportingmaintenance.uis.disruption_typs.DisruptionTypesActivity
import com.app.reportingmaintenance.uis.faculties.FacultiesActivity
import com.app.reportingmaintenance.uis.places.PlacesActivity
import com.app.reportingmaintenance.uis.students.StudentActivity

class DataAdapter(private val facultyDisributionList: MutableList<DataModel>,private val  context: Context) :
    RecyclerView.Adapter<FacultyDisributionViewHolder>() {
    var binding: DataRowBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacultyDisributionViewHolder {

        binding = DataRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FacultyDisributionViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: FacultyDisributionViewHolder, position: Int) {

        val facultyDisribution = facultyDisributionList[position]
        holder.itemView.setOnClickListener(View.OnClickListener {
            if(context is FacultiesActivity){
                val activity = context as FacultiesActivity
                activity.showPlaces(facultyDisributionList[holder.layoutPosition])
            }
        })
        binding!!.imremove.setOnClickListener({
            if(context is FacultiesActivity){
                val activity = context as FacultiesActivity
                activity.remove(facultyDisributionList[holder.layoutPosition])
            }
            else   if(context is PlacesActivity){
                val activity = context as PlacesActivity
                activity.remove(facultyDisributionList[holder.layoutPosition])
            }else if(context is DisruptionTypesActivity){
                val activity = context as DisruptionTypesActivity
                activity.remove(facultyDisributionList[holder.layoutPosition])
            }

        })
        holder.bind(facultyDisribution)
    }

    override fun getItemCount(): Int = facultyDisributionList.size
}

class FacultyDisributionViewHolder(
    private val binding: DataRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(dataModel: DataModel) {
        binding.model = dataModel
    }
}