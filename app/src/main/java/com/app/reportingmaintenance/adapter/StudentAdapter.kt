package com.app.reportingmaintenance.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.app.reportingmaintenance.databinding.UserRowBinding
import com.app.reportingmaintenance.model.UserModel
import com.app.reportingmaintenance.uis.faculties.FacultiesActivity
import com.app.reportingmaintenance.uis.students.StudentActivity

class StudentAdapter(private val context:Context,private val userList: MutableList<UserModel>) :
    RecyclerView.Adapter<UserViewHolder>() {
    var binding: UserRowBinding? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {

        binding = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {

        val user = userList[position]
        binding!!.imremove.setOnClickListener({
            if(context is StudentActivity){
                val activity = context as StudentActivity
                activity.remove(userList[holder.layoutPosition])
            }
        })
        holder.bind(user)
    }

    override fun getItemCount(): Int = userList.size
}

class UserViewHolder(
    private val binding: UserRowBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(userModel: UserModel) {
        binding.model = userModel
    }
}