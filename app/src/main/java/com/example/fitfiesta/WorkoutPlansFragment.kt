package com.example.fitfiesta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.fitfiesta.databinding.FragmentWorkoutPlansBinding


class WorkoutPlansFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    var workoutList = ArrayList<WorkoutPlansData>()
    lateinit var workoutPlansAdapter: WorkoutPlansAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



    }

    private fun addDataToArrayList() {
        workoutList.add(WorkoutPlansData("Cardio",R.drawable.sretching))
        workoutList.add(WorkoutPlansData("Yoga",R.drawable.yoga))
        workoutList.add(WorkoutPlansData("Strength Training",R.drawable.weight_lifting))
        workoutList.add(WorkoutPlansData("Endurance Training",R.drawable.running))
        workoutList.add(WorkoutPlansData("Core Workouts",R.drawable.core_workout))
        workoutList.add(WorkoutPlansData("Isometric Workouts",R.drawable.lunges))

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_workout_plans, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)

        recyclerView.hasFixedSize()
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        workoutPlansAdapter = WorkoutPlansAdapter(workoutList)
        recyclerView.adapter = workoutPlansAdapter

        addDataToArrayList()
        return view
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
