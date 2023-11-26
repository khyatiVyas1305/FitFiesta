package com.example.fitfiesta

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class WorkoutProgressFragment : Fragment() {

    lateinit var stepsProgressBar: CircularProgressBar
    lateinit var caloriesProgressBar: CircularProgressBar
    lateinit var stepsCount: TextView
    lateinit var caloriesCount: TextView
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProgressListAdapter
    var progressArrayList = mutableListOf<String>()



    var userSteps: Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }

        // Initialize the sharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
//
//        // Observe changes to totalSteps and update UI
//        sharedViewModel.totalSteps.observe(viewLifecycleOwner, Observer { steps ->
//            stepsCount.text = steps.toString()
//            stepsProgressBar.apply {
//                setProgressWithAnimation(steps.toFloat())
//            }
//        })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_workout_progress, container, false)

        stepsProgressBar = view.findViewById(R.id.stepsProgress)
        stepsCount = view.findViewById(R.id.stepsCount)
        caloriesProgressBar = view.findViewById(R.id.caloriesProgress)
        caloriesCount = view.findViewById(R.id.caloriesCount)
        recyclerView = view.findViewById(R.id.recyclerViewProgress)

        // Observe changes to totalSteps and update UI
        sharedViewModel.totalSteps.observe(viewLifecycleOwner, Observer { steps ->
            // Check if the fragment's view is available before updating UI
            if (view != null) {
                stepsCount.text = steps.toString()
                stepsProgressBar.apply {
                    setProgressWithAnimation(steps.toFloat())
                }
            }
        })

        //var steps = getStepsFromDataSource()
        var calories = getCaloriesFromDataSource()
        caloriesCount.text = calories.toInt().toString()


        caloriesProgressBar.apply {
            setProgressWithAnimation(calories)
        }


        // Initialize RecyclerView and its adapter
        adapter = ProgressListAdapter(progressArrayList)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter
        Log.d("recyclerview", "list: $progressArrayList")

//
//        // Observe changes in the itemList
//        sharedViewModel.itemList.observe(viewLifecycleOwner, Observer { itemList ->
//            // Update the RecyclerView when the itemList changes
//            Log.d("RecyclerView", "Item count: ${itemList.size}")
//            Log.d("Fragment", "ItemList changed: $itemList")
//            adapter.updateList(itemList)
//        })

        return view
    }


    private fun getCaloriesFromDataSource(): Float{
        var calories = 0F
        var temp = stepsCount.text.toString().toInt()
        when(temp){
            in 0.. 100 -> {
                calories = 20F
            }
            in 100..200 -> {
                calories = 30F
            }
            in 200..400 ->{
                calories = 45F
            }
            in 400..700 ->{
                calories = 50F
            }
            in 700..1400 ->{
                calories = 55F
            }
            in 1400..2000 ->{
                calories = 60F
            }
            in 2000..3000 ->{
                calories = 70F
            }
            in 3000..4000 ->{
                calories = 100F
            }
            in 4000..6000 ->{
                calories = 130F
            }
            else ->{
                calories = 230F
            }
        }
        return calories
    }


    fun updateRecyclerView(newData: String) {
        progressArrayList.add(newData)
        adapter.notifyDataSetChanged()
        Log.d("recyclerview", "Updated list: $progressArrayList")
    }

//    fun receiveData(updatedSteps: Int) {
//        var userSteps = updatedSteps
//        stepsCount.text = updatedSteps.toString()
//        stepsProgressBar.apply {
//            setProgressWithAnimation(userSteps.toFloat())
//        }
//        Log.d("progress", "DATA RECEIVED $userSteps")
//
//
//    }
}
