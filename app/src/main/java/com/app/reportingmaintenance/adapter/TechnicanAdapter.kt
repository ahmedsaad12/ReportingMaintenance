package com.app.reportingmaintenance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reportingmaintenance.databinding.TechnicanRowBinding
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.uis.technicians.TechnicActivity

class TechnicanAdapter(private val context: Context, private val userList: MutableList<UserModel>) :
    RecyclerView.Adapter<TechnicanViewHolder>() {
    var binding: TechnicanRowBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TechnicanViewHolder {

        binding = TechnicanRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TechnicanViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: TechnicanViewHolder, position: Int) {

        val user = userList[position]
        binding!!.imremove.setOnClickListener({
            if(context is TechnicActivity){
                val activity = context as TechnicActivity
                activity.remove(userList[holder.layoutPosition])
            }
        })
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size
}

class TechnicanViewHolder(
    private val binding: TechnicanRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userModel: UserModel) {
        binding.model = userModel
    }
}