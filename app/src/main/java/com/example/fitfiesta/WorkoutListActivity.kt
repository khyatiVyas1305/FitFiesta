package com.example.fitfiesta

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Switch
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

class WorkoutListActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView
    lateinit var planTV: TextView
    lateinit var workoutListAdapter: WorkoutListAdapter
    var workoutsArrayList = ArrayList<WorkoutListData>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)

        recyclerView = findViewById(R.id.recyclerView)
        planTV = findViewById(R.id.planTV)

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        workoutListAdapter = WorkoutListAdapter(workoutsArrayList, this)
        recyclerView.adapter = workoutListAdapter

        addWorkouts()

      workoutListAdapter.onItemClicked = {
          val  intent = Intent(this, WorkoutDetailsActivity::class.java)
          intent.putExtra("workout",it)
          startActivity(intent)
      }




    }

    private fun addWorkouts() {
        //workoutsArrayList.add(WorkoutListData(""))

        val workout = intent.getParcelableExtra<WorkoutPlansData>("workout")

        if (workout != null){
            planTV.text = workout.text
        }

       when(planTV.text){
           "Cardio" -> {
               workoutsArrayList.add(WorkoutListData("Jump Ropes"))
               workoutsArrayList.add(WorkoutListData("Stair Climbing"))
               workoutsArrayList.add(WorkoutListData("Burpees"))
               workoutsArrayList.add(WorkoutListData("Squat Jumps"))
               workoutsArrayList.add(WorkoutListData("High Knees"))
               workoutsArrayList.add(WorkoutListData("Squat"))
               workoutsArrayList.add(WorkoutListData("Jogging in place"))
           }
           "Yoga" -> {
               workoutsArrayList.add(WorkoutListData("Standing Forward Bend"))
               workoutsArrayList.add(WorkoutListData("Extended Triangle Pose"))
               workoutsArrayList.add(WorkoutListData("Legs-up-the-wall Pose"))
               workoutsArrayList.add(WorkoutListData("Bow Pose"))
               workoutsArrayList.add(WorkoutListData("Cobra Pose"))
               workoutsArrayList.add(WorkoutListData("Tree Pose"))
               workoutsArrayList.add(WorkoutListData("Dancer's Pose"))
               workoutsArrayList.add(WorkoutListData("Side Plank Pose"))
               workoutsArrayList.add(WorkoutListData("Fish Pose"))
           }
           "Strength" ->{
               workoutsArrayList.add(WorkoutListData("Squat"))
               workoutsArrayList.add(WorkoutListData("Plank"))
               workoutsArrayList.add(WorkoutListData("Lunge"))
               workoutsArrayList.add(WorkoutListData("Reverse Lunge"))
               workoutsArrayList.add(WorkoutListData("Pull-up"))
               workoutsArrayList.add(WorkoutListData("Calf raise"))
               workoutsArrayList.add(WorkoutListData("Navasana"))
               workoutsArrayList.add(WorkoutListData("Triceps dips"))
               workoutsArrayList.add(WorkoutListData("Push-ups"))
           }
           "Endurance" ->{
               workoutsArrayList.add(WorkoutListData("Plank"))
               workoutsArrayList.add(WorkoutListData("Body weight squats"))
               workoutsArrayList.add(WorkoutListData("Walking lunges"))
               workoutsArrayList.add(WorkoutListData("Push-ups"))
               workoutsArrayList.add(WorkoutListData("Crunches"))
               workoutsArrayList.add(WorkoutListData("Weight lifting"))
               workoutsArrayList.add(WorkoutListData("Shoulder stretching"))
           }
           "Core" ->{
               workoutsArrayList.add(WorkoutListData("Sit-ups"))
               workoutsArrayList.add(WorkoutListData("Flutter kicks"))
               workoutsArrayList.add(WorkoutListData("Bridge"))
               workoutsArrayList.add(WorkoutListData("Dead bug"))
               workoutsArrayList.add(WorkoutListData("Russian twist"))
               workoutsArrayList.add(WorkoutListData("Mountain climber"))
               workoutsArrayList.add(WorkoutListData("Bicycle crunch"))
               workoutsArrayList.add(WorkoutListData("V-ups"))
               workoutsArrayList.add(WorkoutListData("Plank jack"))
           }
           "Isometric" -> {
               workoutsArrayList.add(WorkoutListData("Low Squat"))
               workoutsArrayList.add(WorkoutListData("Split Squat"))
               workoutsArrayList.add(WorkoutListData("Wall sits"))
               workoutsArrayList.add(WorkoutListData("Isometric Push-up"))
               workoutsArrayList.add(WorkoutListData("Dumbbell Curl"))
               workoutsArrayList.add(WorkoutListData("Dead Hang"))
               workoutsArrayList.add(WorkoutListData("Hollow-Body Hold"))
               workoutsArrayList.add(WorkoutListData("Lateral Shoulder Raise"))
           }
       }

//        if (planTV.text == "Cardio"){
//            workoutsArrayList.add(WorkoutListData("Jump Ropes"))
//            workoutsArrayList.add(WorkoutListData("Stair Climbing"))
//            workoutsArrayList.add(WorkoutListData("Burpees"))
//            workoutsArrayList.add(WorkoutListData("Squat Jumps"))
//            workoutsArrayList.add(WorkoutListData("High Knees"))
//            workoutsArrayList.add(WorkoutListData("Squat"))
//            workoutsArrayList.add(WorkoutListData("Jogging in place"))
//        }
    }
}