package com.example.fitfiesta

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class WorkoutListAdapter(var workoutsArrayList: ArrayList<WorkoutListData>) : RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder>() {

    var onItemClicked: ((WorkoutListData) -> Unit)? = null

    class MyViewHolder  (itemView: View):RecyclerView.ViewHolder(itemView){
        val cardText = itemView.findViewById<TextView>(R.id.cardText)
        val favoriteButton = itemView.findViewById<ImageButton>(R.id.favoriteButton)

        private var isFavorited = false

        init {
            favoriteButton.setOnClickListener {
                isFavorited = !isFavorited

                if(isFavorited){
                    favoriteButton.setImageResource(R.drawable.ic_heart_filled)
                    itemView.context?.let {
                        Toast.makeText(it, "Added  to favorites", Toast.LENGTH_SHORT).show()
                    }
                } else{
                    favoriteButton.setImageResource(R.drawable.ic_heart_outline)
                    itemView.context?.let {
                        Toast.makeText(it, "Removed from favorites", Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.workout_list_item,parent,false)
        return MyViewHolder(view)
    }


    override fun getItemCount(): Int {
        return workoutsArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val workout = workoutsArrayList[position]
        holder.cardText.text = workout.exercise

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(workout)
        }
    }

}