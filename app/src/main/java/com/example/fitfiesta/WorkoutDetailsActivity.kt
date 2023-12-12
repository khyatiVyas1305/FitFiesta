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