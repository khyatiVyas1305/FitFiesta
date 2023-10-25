package com.example.fitfiesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import org.w3c.dom.Text

class WorkoutDetailsActivity : AppCompatActivity() {

    lateinit var workoutName: TextView
    lateinit var workoutImg: ImageView
    lateinit var description: TextView
    lateinit var time: TextView
    lateinit var sets: TextView
    lateinit var calories: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_details)

        workoutName = findViewById(R.id.workoutName)
        workoutImg = findViewById(R.id.workoutImg)
        description = findViewById(R.id.description)
        time = findViewById(R.id.workoutTime)
        sets = findViewById(R.id.workoutSetsTV)
        calories = findViewById(R.id.workoutCaloriesTV)

        val workout = intent.getParcelableExtra<WorkoutListData>("workout")
        if (workout != null) {
            workoutName.text = workout.exercise
        }

        when(workoutName.text){
            "Jump Ropes" ->{
                workoutImg.setImageResource(R.drawable.jump_ropes)
                description.text = "Begin with a light warm-up to prepare your muscles. Learn basic " +
                        "jumping techniques and progress to more advanced moves. Mix in different " +
                        "jumping styles like high knees, double unders, and crossovers to keep things exciting."
                time.text = "15 Min"
                sets.text = "5X20 sets"
                calories.text = "190 calories"

            }
            "Stair Climbing" ->{
                workoutImg.setImageResource(R.drawable.stair_climb)
                description.text = "Begin with a light warm-up to prepare your muscles." +
                        "Find a set of stairs and start climbing at a moderate pace." +
                        "Increase intensity by incorporating interval training—climb briskly for a " +
                        "set period, then recover with a slower descent. " +
                        "Mix it up with sideways steps, skipping steps, or even lunges on the way up."
                time.text = "10 Min"
                sets.text = "2X20 sets"
                calories.text = "250 calories"

            }
            "Lunge" ->{
                workoutImg.setImageResource(R.drawable.lunges)
                description.text = "Begin with a gentle warm-up to prepare your muscles for the workout. " +
                        "Maintain an upright posture with shoulders back and core engaged during each lunge. " +
                        "Ensure proper alignment of your knee over the ankle to protect your joints. " +
                        "Breathe rhythmically, exhaling as you lunge and inhaling as you return to the starting position."
                time.text = "30 Min"
                sets.text = "10X3 sets"
                calories.text = "320 calories"
            }
        }

    }
}