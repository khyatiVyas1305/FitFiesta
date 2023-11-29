package com.example.fitfiesta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FavWorkoutAdapter(private val favWorkoutList: List<String>) : RecyclerView.Adapter<FavWorkoutAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutText: TextView = itemView.findViewById(R.id.favWorkoutText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fav_workout_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.workoutText.text = favWorkoutList[position]
    }

    override fun getItemCount(): Int {
        return favWorkoutList.size
    }
}