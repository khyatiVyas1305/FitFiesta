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
import org.w3c.dom.Text
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

    lateinit var nutritionFactHeading: TextView
    lateinit var vitaminFactTextView: TextView
    lateinit var foodSourceFactTextView: TextView
    lateinit var mealIdeasFactTextView: TextView

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

        nutritionFactHeading = view.findViewById(R.id.nutritionFactHeading)
        vitaminFactTextView = view.findViewById(R.id.vitaminFactTextView)
        foodSourceFactTextView = view.findViewById(R.id.foodSourceFactTextView)
        mealIdeasFactTextView = view.findViewById(R.id.mealIdeasFactTextView)

        val calendar = Calendar.getInstance()
        val day = calendar[Calendar.DAY_OF_WEEK]

        when (day) {
            Calendar.SUNDAY -> {
                randomTV.text = "Cardio"
                randomImg.setImageResource(R.drawable.sretching)
                nutritionFactHeading.text="Vitamin C"
                vitaminFactTextView.text="Benefits: Boosts immune function, collagen production, and aids in iron absorption."
                foodSourceFactTextView.text="Food Sources: Oranges, strawberries, kiwi, bell peppers, broccoli."
                mealIdeasFactTextView.text="Meal Ideas: Start the day with a citrus fruit salad. Lunch could include a stir-fry with bell peppers and broccoli. For dinner, consider grilled chicken with a side of strawberry salsa."
            }
            Calendar.MONDAY -> {
                randomTV.text = "Yoga"
                randomImg.setImageResource(R.drawable.yoga)
                nutritionFactHeading.text="Vitamin D"
                vitaminFactTextView.text="Benefits: Supports bone health, enhances mood, and regulates calcium absorption."
                foodSourceFactTextView.text="Food Sources: Fatty fish (salmon, mackerel), fortified dairy or plant-based milk, eggs, mushrooms."
                mealIdeasFactTextView.text="Meal Ideas: Breakfast with scrambled eggs or a mushroom omelet. Lunch could include a salmon salad, and for dinner, grilled salmon with steamed vegetables."
            }
            Calendar.TUESDAY -> {
                randomTV.text = "Strength Training"
                randomImg.setImageResource(R.drawable.weight_lifting)
                nutritionFactHeading.text="Vitamin A"
                vitaminFactTextView.text="Benefits: Essential for vision, immune function, and skin health."
                foodSourceFactTextView.text="Food Sources: Carrots, sweet potatoes, spinach, kale, liver, eggs."
                mealIdeasFactTextView.text="Meal Ideas: Carrot and sweet potato soup for lunch. Spinach and kale salad with eggs for dinner. Consider a fruit salad containing mangoes for dessert."

            }
            Calendar.WEDNESDAY -> {
                randomTV.text = "Endurance Training"
                randomImg.setImageResource(R.drawable.running)
                nutritionFactHeading.text="Vitamin E"
                vitaminFactTextView.text="Benefits: Acts as an antioxidant, supports skin health, and boosts immunity."
                foodSourceFactTextView.text="Food Sources: Nuts (almonds, hazelnuts), seeds (sunflower seeds), spinach, avocado."
                mealIdeasFactTextView.text="Meal Ideas: Breakfast smoothie with spinach, almond butter, and sunflower seeds. Lunch could include an avocado salad, and for dinner, almond-crusted baked chicken with vegetables."
            }
            Calendar.THURSDAY -> {
                randomTV.text = "Core Workouts"
                randomImg.setImageResource(R.drawable.core_workout)
                nutritionFactHeading.text="Vitamin K"
                vitaminFactTextView.text="Benefits: Aids in blood clotting, bone health, and may reduce the risk of heart disease."
                foodSourceFactTextView.text="Food Sources: Kale, spinach, broccoli, Brussels sprouts, green beans."
                mealIdeasFactTextView.text="Meal Ideas: Start the day with a kale and spinach smoothie. Lunch could include a broccoli salad, and for dinner, grilled fish with a side of sautéed Brussels sprouts."

            }
            Calendar.FRIDAY -> {
                randomTV.text = "Isometric Workouts"
                randomImg.setImageResource(R.drawable.lunges)
                nutritionFactHeading.text="Amino Acids"
                vitaminFactTextView.text="Benefits: Builds and repairs tissues, supports muscle health, and is essential for enzymes and hormones."
                foodSourceFactTextView.text="Food Sources: Chicken, turkey, fish, eggs, dairy, tofu, quinoa, chia seeds."
                mealIdeasFactTextView.text="Meal Ideas: Breakfast with scrambled eggs. Lunch could include a quinoa salad with tofu, and for dinner, grilled chicken or fish with a side of vegetables."
            }
            Calendar.SATURDAY -> {
                randomTV.text = "Endurance Training"
                randomImg.setImageResource(R.drawable.running)
                nutritionFactHeading.text="Plant-Based Proteins"
                vitaminFactTextView.text="Benefits: Provides protein while often being lower in saturated fats and cholesterol."
                foodSourceFactTextView.text="Food Sources: Lentils, chickpeas, beans, tofu, edamame, nuts, seeds."
                mealIdeasFactTextView.text="Meal Ideas: Breakfast smoothie with almond milk and chia seeds. Lunch could include a lentil soup or chickpea salad, and for dinner, tofu stir-fry with mixed vegetables."
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

