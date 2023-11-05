package com.example.fitfiesta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.mikhaellopez.circularprogressbar.CircularProgressBar


class WorkoutProgressFragment : Fragment() {

    lateinit var stepsProgressBar: CircularProgressBar
    lateinit var caloriesProgressBar: CircularProgressBar
    lateinit var stepsCount: TextView
    lateinit var caloriesCount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
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

        var steps = stepsCount.toString()
        var calories = caloriesCount.toString()

        stepsProgressBar.apply {
            setProgressWithAnimation(steps.toFloat())
        }

        caloriesProgressBar.apply {
            setProgressWithAnimation(calories.toFloat())
        }

        return view
    }
}