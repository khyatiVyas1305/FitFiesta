package com.example.fitfiesta

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale


class WorkoutPlansFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    var workoutList = ArrayList<WorkoutPlansData>()
    lateinit var workoutPlansAdapter: WorkoutPlansAdapter

    private fun addDataToArrayList() {
        workoutList.add(WorkoutPlansData("Cardio",R.drawable.sretching))
        workoutList.add(WorkoutPlansData("Yoga",R.drawable.yoga))
        workoutList.add(WorkoutPlansData("Strength",R.drawable.weight_lifting))
        workoutList.add(WorkoutPlansData("Endurance",R.drawable.running))
        workoutList.add(WorkoutPlansData("Core",R.drawable.core_workout))
        workoutList.add(WorkoutPlansData("Isometric",R.drawable.lunges))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_workout_plans, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        searchView = view.findViewById(R.id.searchView)

        //RecyclerView
        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        workoutPlansAdapter = WorkoutPlansAdapter(workoutList)
        recyclerView.adapter = workoutPlansAdapter

        workoutPlansAdapter.onItemClick = {
            val intent = Intent(requireContext(), WorkoutListActivity::class.java)
            intent.putExtra("workout", it)
            startActivity(intent)
        }

        addDataToArrayList()

        //SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterWorkouts(newText)
                return true
            }

        })

        return view
    }

    private fun filterWorkouts(searchText: String?) {
        if(searchText != null){
            val filteredWorkout = ArrayList<WorkoutPlansData>()
            for (i in workoutList){
                if(i.text.lowercase(Locale.ROOT).contains(searchText)){
                    filteredWorkout.add(i)
                }
            }
            if(filteredWorkout.isEmpty()){
                Toast.makeText(requireContext(), "Sorry! this workout is not available.",Toast.LENGTH_SHORT).show()
            }else{
                workoutPlansAdapter.updateFilterList(filteredWorkout)
            }
        }
    }

    companion object {

        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WorkoutPlansFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}



