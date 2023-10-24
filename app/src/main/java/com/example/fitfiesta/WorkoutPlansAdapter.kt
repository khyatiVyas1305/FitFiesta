package com.example.fitfiesta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class WorkoutPlansAdapter(var workoutList: ArrayList<WorkoutPlansData>)  :RecyclerView.Adapter<WorkoutPlansAdapter.MyViewHolder>() {

    var onItemClick : ((WorkoutPlansData) -> Unit)? = null

    class MyViewHolder (itemView: View):RecyclerView.ViewHolder(itemView){
        val cardImage = itemView.findViewById<ImageView>(R.id.cardImg)
        val cardText = itemView.findViewById<TextView>(R.id.cardText)
    }

    fun updateFilterList(workoutList: ArrayList<WorkoutPlansData>){
        this.workoutList = workoutList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WorkoutPlansAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: WorkoutPlansAdapter.MyViewHolder, position: Int) {
        val workout = workoutList[position]
        holder.cardImage.setImageResource(workout.image)
        holder.cardText.text = workout.text

        holder.itemView.setOnClickListener {
           onItemClick?.invoke(workout)
        }
    }

    override fun getItemCount(): Int {
        return workoutList.size
    }
}