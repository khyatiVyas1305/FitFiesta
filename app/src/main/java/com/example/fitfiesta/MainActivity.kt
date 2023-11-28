package com.example.fitfiesta

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.fitfiesta.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedViewModel: SharedViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        sharedViewModel = ViewModelProvider(this).get(SharedViewModel::class.java)


        //BOTTOM NAVIGATION
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home -> replaceFragment(HomeFragment())
                R.id.workoutProgress -> replaceFragment(WorkoutProgressFragment())
                R.id.workoutPlans -> replaceFragment(WorkoutPlansFragment())
                R.id.favouriteWorkouts -> replaceFragment(FavouriteWorkoutsFragment())
                R.id.profile -> replaceFragment(ProfileFragment())

                else ->{


                }


            }
            true
        }

    }


    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }
//
//    override fun passData(data: Int) {
//        val bundle = Bundle()
//        bundle.putInt("steps",data)
//
//        val workoutProgressFragment = WorkoutProgressFragment()
//        workoutProgressFragment.arguments = bundle
//    }
}