package com.example.fitfiesta

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

class WorkoutListAdapter(
    var workoutsArrayList: ArrayList<WorkoutListData>,
    private val context: Context // Pass the context to access SharedPreferences
) : RecyclerView.Adapter<WorkoutListAdapter.MyViewHolder>() {

    var onItemClicked: ((WorkoutListData) -> Unit)? = null

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val cardText = itemView.findViewById<TextView>(R.id.cardText)
        val favoriteButton = itemView.findViewById<ImageButton>(R.id.favoriteButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.workout_list_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return workoutsArrayList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val workout = workoutsArrayList[position]
        holder.cardText.text = workout.exercise

        val sharedPrefs = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val favWorkoutList = sharedPrefs.getStringSet("favWorkoutList", LinkedHashSet())

        holder.itemView.setOnClickListener {
            onItemClicked?.invoke(workout)
        }

        holder.favoriteButton.setOnClickListener {
            val isFavorited = !favWorkoutList?.contains(workout.exercise)!!

            if (isFavorited) {
                holder.favoriteButton.setImageResource(R.drawable.ic_heart_filled)
                Toast.makeText(context, "Added ${workout.exercise} to favorites", Toast.LENGTH_SHORT).show()
                favWorkoutList?.add(workout.exercise)
            } else {
                holder.favoriteButton.setImageResource(R.drawable.ic_heart_outline)
                Toast.makeText(context, "Removed ${workout.exercise} from favorites", Toast.LENGTH_SHORT).show()
                favWorkoutList?.remove(workout.exercise)
            }

            sharedPrefs.edit().putStringSet("favWorkoutList", favWorkoutList).apply()
            Log.d("List Contents", favWorkoutList.toString())
        }
    }
}
