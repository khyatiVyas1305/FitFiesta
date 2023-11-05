package com.example.fitfiesta

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import java.util.Calendar

class HomeFragment : Fragment() {


    lateinit var randomTV: TextView
    lateinit var randomImg : ImageView
//    lateinit var tvStepsTaken: TextView
//    lateinit var stepsCounter: CircularProgressBar
    private lateinit var sharedPreferences: SharedPreferences

//
//    private var sensorManager: SensorManager? =null
//    private var running = false
//    private var totalSteps = 0f
//    private var previousTotalSteps = 0f


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
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        randomTV = view.findViewById(R.id.cardTextView)
        randomImg = view.findViewById(R.id.cardImage)
//        tvStepsTaken = view.findViewById(R.id.stepsTaken)
//        stepsCounter = view.findViewById(R.id.stepsCounter)

        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]

        when (day) {
            Calendar.SUNDAY -> {
                randomTV.text = "Cardio"
                randomImg.setImageResource(R.drawable.sretching)
            }
            Calendar.MONDAY -> {
                randomTV.text = "Yoga"
                randomImg.setImageResource(R.drawable.yoga)
            }
            Calendar.TUESDAY -> {
                randomTV.text = "Strength Training"
                randomImg.setImageResource(R.drawable.weight_lifting)
            }
            Calendar.WEDNESDAY -> {
                randomTV.text = "Endurance Training"
                randomImg.setImageResource(R.drawable.running)
            }
            Calendar.THURSDAY -> {
                randomTV.text = "Core Workouts"
                randomImg.setImageResource(R.drawable.core_workout)
            }
            Calendar.FRIDAY -> {
                randomTV.text = "Isometric Workouts"
                randomImg.setImageResource(R.drawable.lunges)
            }
            Calendar.SATURDAY -> {
                randomTV.text = "Cardio"
                randomImg.setImageResource(R.drawable.sretching)
            }
        }

        //STEPS COUNTER
//        sensorManager = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
//        loadData()
//        resetData()


        return view
    }

    //SENSOR MANAGE
//    override fun onResume() {
//        super.onResume()
//        running = true
//        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
//
//        if(stepSensor == null){
//            Toast.makeText(requireContext(),"No sensor found on this device", Toast.LENGTH_SHORT).show()
//        }else{
//            sensorManager?.registerListener(this,stepSensor,SensorManager.SENSOR_DELAY_UI)
//        }
//    }
//
//    override fun onSensorChanged(event: SensorEvent?) {
//        if(running){
//            totalSteps = event!!.values[0]
//            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
//            tvStepsTaken.text = ("$currentSteps")
//
//            stepsCounter.apply {
//                setProgressWithAnimation(currentSteps.toFloat())
//            }
//        }
//    }
//
//    private fun saveData() {
//        sharedPreferences = requireActivity().getSharedPreferences("mySharedPref",Context.MODE_PRIVATE)
//        val editor = sharedPreferences.edit()
//        editor.putFloat("key1",previousTotalSteps)
//        editor.apply()
//    }
//
//    private fun resetData(){
//        tvStepsTaken.setOnClickListener {
//            Toast.makeText(requireContext(),"Long press to reset the counter", Toast.LENGTH_SHORT).show()
//        }
//        tvStepsTaken.setOnLongClickListener{
//            previousTotalSteps = totalSteps
//            tvStepsTaken.text = 0.toString()
//            saveData()
//            true
//        }
//    }
//
//    private fun loadData(){
//        val sharedPreferences = requireActivity().getSharedPreferences("mySharedPref", Context.MODE_PRIVATE)
//        val savedNumber = sharedPreferences.getFloat("key1",0f)
//        Log.d("HomeFragment", "$savedNumber")
//        previousTotalSteps = savedNumber
//    }
//
//    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
//
//    }

//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment HomeFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            HomeFragment().apply {
//                arguments = Bundle().apply {
//                }
//            }
//    }

}

