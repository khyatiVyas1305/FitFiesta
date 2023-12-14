package com.example.fitfiesta

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.w3c.dom.Text
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class WorkoutDetailsActivity : AppCompatActivity() {

    lateinit var workoutName: TextView
    lateinit var workoutImg: ImageView
    lateinit var description: TextView
    lateinit var time: TextView
    lateinit var sets: TextView
    lateinit var calories: TextView
    lateinit var downloadBtn: Button
    lateinit var view: View
    private val REQUEST_WRITE_EXTERNAL_STORAGE = 1

    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_details)

        workoutName = findViewById(R.id.workoutName)
        workoutImg = findViewById(R.id.workoutImg)
        description = findViewById(R.id.description)
        time = findViewById(R.id.workoutTime)
        sets = findViewById(R.id.workoutSetsTV)
        calories = findViewById(R.id.workoutCaloriesTV)
        downloadBtn = findViewById(R.id.downloadBtn)
        view = findViewById(R.id.DetailView)

        downloadBtn.setOnClickListener {
            // Capture the layout view as a bitmap
            val bitmap = captureView(findViewById(android.R.id.content))

            if (Environment.isExternalStorageManager()) {
                // You have the MANAGE_EXTERNAL_STORAGE permission
                // Proceed with your operations
                generatePdf(bitmap)
            } else {
                // You don't have the MANAGE_EXTERNAL_STORAGE permission
                // Request the permission from the user
                val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                intent.data = Uri.parse("package:$packageName")
                startActivityForResult(intent, REQUEST_WRITE_EXTERNAL_STORAGE)
            }

//            // Check if the app has permission, and request if not
//            if (checkStoragePermission()) {
//                // Your logic if permission is granted
//            } else {
//                requestStoragePermission()
//            }
        }

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
            "Burpees" ->{
                workoutImg.setImageResource(R.drawable.burpees_set)
                description.text = "Begin in a standing position with your feet shoulder-width apart and arms at your sides." +
                        "Lower your body into a squat position by bending your knees and bringing your hands to the floor in front of you." +
                        "From the squat position, jump your feet back, landing in a plank position with your body in a straight line from head to heels." +
                        "Perform a push-up by lowering your chest to the ground and then pushing back up to the plank position." +
                        "Jump your feet back toward your hands, returning to the squat position." +"Explosively jump straight up into the air, reaching your arms overhead." +
                        "Land softly and immediately go back into the next repetition by moving into the squat position."
                time.text = "15-20 Min"
                sets.text = "1X20 sets"
                calories.text = "280 calories"

            }
            "Squat Jumps" ->{
                workoutImg.setImageResource(R.drawable.squat_jumps)
                description.text = "Stand with your feet shoulder-width apart." + "Keep your chest up, shoulders back, and engage your core muscles." +
                        "Lower your body into a squat position by bending your knees and pushing your hips back." +
                        "Keep your back straight, and make sure your knees are in line with your toes." +
                        "From the squat position, explosively push through your feet and jump straight up into the air." +
                        "Swing your arms back during the descent into the squat and then swing them forward as you jump." +
                        "Land softly and immediately go back into the next repetition by moving into the squat position. " +
                        "As you descend from the jump, land softly on the balls of your feet." +
                        "Bend your knees slightly to absorb the impact, and ensure your knees are in line with your toes." +
                        "After landing, immediately go back into the squat position and prepare for the next jump." +
                        "Perform the exercise in a continuous, fluid motion."

                time.text = "15 Min"
                sets.text = "1X15 sets"
                calories.text = "120 calories"

            }
            "High Knees" ->{
                workoutImg.setImageResource(R.drawable.high_knees)
                description.text = "Stand with your feet hip-width apart and arms hanging at your sides." +
                        "Tighten your core muscles to stabilize your torso." +
                        "Lift your right knee as high as possible towards your chest while simultaneously swinging your left arm forward.." +
                        "Keep your back straight, and use your core to lift your knee." +
                        "As you lower your right knee, immediately lift your left knee towards your chest while swinging your right arm forward." +
                        "Continue alternating legs in a running or marching motion." +
                        "Breathe in a controlled manner, focusing on deep breaths as you maintain the high knees movement."

                time.text = "5 Min"
                sets.text = "2X10 sets"
                calories.text = "126 calories"

            }
            "Squat" ->{
                workoutImg.setImageResource(R.drawable.squats)
                description.text = "Stand with your feet shoulder-width apart. Keep your chest up, shoulders back, and your gaze forward." +
                        "Tighten your core muscles to stabilize your spine." +
                        "Hinge at your hips and begin to lower your body as if you're sitting back into a chair." +
                        "Bend your knees and lower your hips down and back." +
                        "Lower your body until your thighs are parallel to the ground or as far as your flexibility allows." +
                        "Inhale as you lower into the squat. Exhale as you push back up."

                time.text = "25 Min"
                sets.text = "3X10 sets"
                calories.text = "225 calories"

            }
            "Jogging in place" ->{
                workoutImg.setImageResource(R.drawable.jogging_in_place)
                description.text = "Stand with your feet hip-width apart. Keep your posture upright, shoulders back, and core engaged. " +
                        "Lift your knees up and start jogging in place. " +
                        "Aim to bring your knees to a height that feels comfortable for your fitness level. " +
                        "Swing your arms naturally in coordination with your leg movements. " +
                        "Jog at a pace that suits your fitness level. Breathe naturally and steadily. "

                time.text = "10 Min"
                sets.text = "1 sets"
                calories.text = "130 calories"

            }
            "Standing Forward Bend" ->{
                workoutImg.setImageResource(R.drawable.standing_forward_bending)
                description.text = "Begin in a standing position with your feet hip-width apart. Allow your arms to hang down by your sides. " +
                        "Inhale deeply, elongating your spine as you do so. On the exhale, start to hinge at your hips, leading with your chest." +
                        "Keep your back straight as you fold forward, avoiding rounding your spine. " +
                        "Bring your hands to the floor on either side of your feet if possible." +
                        "If reaching the floor is challenging, you can place your hands on your shins or use yoga blocks for support. " +
                        "Stay in the forward bend for 30 seconds to 1 minute, breathing deeply and relaxing into the stretch."

                time.text = "10 Min"
                sets.text = "1X10 sets"
                calories.text = "100 calories"

            }
            "Extended Triangle Pose" ->{
                workoutImg.setImageResource(R.drawable.extended_triangle_pose)
                description.text = "Begin in a standing position at the top of your mat with your feet about 3 to 4 feet apart. " +
                        "Ensure your feet are parallel to each other. Turn your right foot out 90 degrees so that your toes are pointing to the top of the mat. " +
                        "Keep your left foot slightly turned in, maintaining a comfortable angle. " +
                        "Extend your arms parallel to the floor, reaching them out to the sides. Keep your shoulders relaxed and facing forward. `" +
                        "Keep your back straight as you fold forward, avoiding rounding your spine. " +
                        "Bring your hands to the floor on either side of your feet if possible." +
                        "If reaching the floor is challenging, you can place your hands on your shins or use yoga blocks for support. " +
                        "Allow your head to hang naturally, with your neck relaxed."

                time.text = "10 Min"
                sets.text = "1X10 sets"
                calories.text = "100 calories"

            }
            "Lunge" ->{
                workoutImg.setImageResource(R.drawable.lunges)
                description.text = "Begin with a gentle warm-up to prepare your muscles for the workout. " +
                        "Maintain an upright posture with shoulders back and core engaged during each lunge. " +
                        "Ensure proper alignment of your knee over the ankle to protect your joints. " +
                        "Breathe rhythmically, exhaling as you lunge and inhaling as you return to the starting position. "
                time.text = "30 Min"
                sets.text = "10X3 sets"
                calories.text = "320 calories"
            }
        }

    }

    private fun captureView(view: View): Bitmap {
        val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val background = view.background

        if (background != null) {
            background.draw(canvas)
        } else {
            canvas.drawColor(Color.WHITE)
        }

        view.draw(canvas)
        return bitmap
    }

    private fun generatePdf(bitmap: Bitmap) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)

        val canvas = page.canvas
        val paint = Paint()

        canvas.drawBitmap(bitmap, 0f, 0f, paint)
        pdfDocument.finishPage(page)

        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val pdfFileName = "PDF_$timeStamp.pdf"
        val storageDir: File? = Environment.getExternalStorageDirectory()


        try {
            val pdfFile = File.createTempFile(pdfFileName, ".pdf", storageDir)
            val outputStream = FileOutputStream(pdfFile)

            pdfDocument.writeTo(outputStream)
            pdfDocument.close()
            outputStream.flush()
            outputStream.close()

            Toast.makeText(applicationContext,"\"PDF saved successfully: ${pdfFile.absolutePath}\"",Toast.LENGTH_SHORT).show()
            Log.d("PDF", "PDF saved successfully: ${pdfFile.absolutePath}")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.e("PDF", "Error saving PDF: ${e.message}")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_WRITE_EXTERNAL_STORAGE) {
            // Check if the user granted the MANAGE_EXTERNAL_STORAGE permission
            if (if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Environment.isExternalStorageManager()
                } else {
                    TODO("VERSION.SDK_INT < R")
                }
            ) {
                // Permission granted, proceed with your operations
            } else {
                // Permission not granted, handle accordingly
                Toast.makeText(applicationContext,"Permission Denied",Toast.LENGTH_SHORT).show()
                Log.d("permission","Permission denied")
            }
        }
    }

}