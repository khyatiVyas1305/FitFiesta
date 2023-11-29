package com.example.fitfiesta

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FavouriteWorkoutsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FavWorkoutAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favourite_workouts, container, false)

        recyclerView = view.findViewById(R.id.recyclerView) // Replace with your RecyclerView ID
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = FavWorkoutAdapter(getFavWorkoutListFromSharedPreferences())
        recyclerView.adapter = adapter

        return view
    }

    private fun getFavWorkoutListFromSharedPreferences(): List<String> {
        val sharedPrefs: SharedPreferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet("favWorkoutList", LinkedHashSet())?.toList() ?: emptyList()
    }
}
