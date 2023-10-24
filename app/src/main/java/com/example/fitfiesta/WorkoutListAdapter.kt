package com.example.fitfiesta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutListAdapter(var workoutsArrayList: ArrayList<WorkoutListData>) : RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder>() {

    class MyViewHolder  (itemView: View):RecyclerView.ViewHolder(itemView){
        val cardText = itemView.findViewById<TextView>(R.id.cardText)
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_list_item,parent,false)
        return WorkoutListAdapter.MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return workoutsArrayList.size
    }

    override fun onBindViewHolder(holder: WorkoutListAdapter.MyViewHolder, position: Int) {
        holder.cardText.text = workoutsArrayList[position].exercise
    }

}