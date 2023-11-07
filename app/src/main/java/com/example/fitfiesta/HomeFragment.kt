package com.example.fitfiesta

import android.content.Context
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
import androidx.lifecycle.ViewModelProvider
import java.util.Calendar

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment(), SensorEventListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var randomTV: TextView
    lateinit var randomImg : ImageView

    private var sensorManager: SensorManager? = null
    private var stepCounter: Sensor? = null
    private var steps = 0
    private lateinit var stepsTextView: TextView
    private lateinit var progressBar: View
    private val MAX_STEPS = 6000
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        sensorManager = activity?.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        stepCounter = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        stepCounter?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }

        // Initialize the sharedViewModel
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        randomTV = view.findViewById(R.id.cardTextView)
        randomImg = view.findViewById(R.id.cardImage)

        stepsTextView = view.findViewById(R.id.stepsTextView)
        progressBar = view.findViewById(R.id.progressBar)

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


//        stepsDataCommunicator = requireActivity() as StepsDataCommunicator
//        stepsDataCommunicator.passData(steps)
//        Log.d("home","$steps")

        return view
        
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            if (it.sensor.type == Sensor.TYPE_STEP_COUNTER) {
                steps = it.values[0].toInt()
                stepsTextView.text = steps.toString()

                val progress = if (steps > MAX_STEPS) MAX_STEPS else steps
                val width = (progress * resources.displayMetrics.widthPixels / MAX_STEPS)
                progressBar.layoutParams.width = width
                progressBar.requestLayout()

                // Update the sharedViewModel with the new step count
                sharedViewModel.totalSteps.value = steps

                // val workoutProgressFragment = WorkoutProgressFragment()

//                val bundle = Bundle()
//                bundle.putInt("steps",steps)
//                workoutProgressFragment.arguments = bundle

//        parentFragmentManager.beginTransaction().apply {
//            replace(R.id.stepsCount, workoutProgressFragment)
//                .commit()
//        }


//                (requireActivity().supportFragmentManager.findFragmentByTag("workoutProgressFragment") as WorkoutProgressFragment?)?.receiveData(
//                    20
//                )

            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onResume() {
        super.onResume()
        stepCounter?.let {
            sensorManager?.registerListener(this, it, SensorManager.SENSOR_DELAY_UI)
        }
    }
}

