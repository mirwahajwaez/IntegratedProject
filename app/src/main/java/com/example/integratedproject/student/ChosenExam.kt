package com.example.integratedproject.student

import android.Manifest
import android.app.ActionBar
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.integratedproject.MainActivity
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper


class ChosenExam : AppCompatActivity(), LifecycleObserver {
    private lateinit var location: String
    private lateinit var latitude: String
    private lateinit var longitude: String
    private var databaseHelper: DatabaseHelper? = null
    private lateinit var examId: String
    private lateinit var examName: String
    private lateinit var chosenStudent: String
    private lateinit var answers: String
    var counter = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        setContentView(R.layout.activity_chosen_exam)
        location = getLocationWithCheckNetworkAndGPS(this).toString()

        latitude = location.split(',')[0].split(' ')[1]
        longitude = location.split(',')[1].split(' ')[0]
        databaseHelper = DatabaseHelper(this)

        examId = intent.getStringExtra("EXAM_ID").toString()
        examName = intent.getStringExtra("EXAM_NAME").toString()
        chosenStudent = intent.getStringExtra("SELECTED_STUDENT").toString()
        answers = intent.getStringExtra("ANSWERS").toString()



        val examenNaam = findViewById<TextView>(R.id.textViewExamen)
        examenNaam.text = examName

        val endExam = findViewById<Button>(R.id.buttonExamenStoppen)
        endExam.setOnClickListener {
            databaseHelper!!.addStudentExam(chosenStudent, examId, longitude, latitude,counter, answers )


            intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            Toast.makeText(this, "Exam has been saved", Toast.LENGTH_SHORT).show()
        }


        createQuestionList()
    }


    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearLayoutQuestionsExam) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val questions: Array<Array<String>> = databaseHelper!!.allQuestions(examId)

        if(questions.isNotEmpty()) {
            for (question in questions) {
                val ll = LinearLayout(this)
                ll.orientation = LinearLayout.HORIZONTAL

                val questionSplitted = question[3].split(';')

                val btn = Button(this)
                btn.text = questionSplitted[0]
                btn.layoutParams = params

                btn.setOnClickListener {
                    intent = Intent(this, ChosenQuestionActivity::class.java)
                    intent.putExtra("QUESTION", question)
                    intent.putExtra("EXAM_NAME", examName)
                    intent.putExtra("EXAM_ID", examId)
                    intent.putExtra("SELECTED_STUDENT", chosenStudent)
                    intent.putExtra("ANSWERS", answers)

                    startActivity(intent)
                }

                val typeText = TextView(this)
                typeText.layoutParams = params
                when(question[2]) {
                    "1" -> typeText.text = getString(R.string.multiple_choice)
                    "2" -> typeText.text = getString(R.string.code_correct)
                    "3" -> typeText.text = getString(R.string.open_question)
                }

                ll.addView(btn)
                ll.addView(typeText)
                lm.addView(ll)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onAppBackgrounded() {
        //App in background
        counter++
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onAppForegrounded() {
        // App in foreground
        Toast.makeText(this, "Student has left exam: $counter times", Toast.LENGTH_SHORT).show()
    }

    private fun getLocationWithCheckNetworkAndGPS(mContext: Context): Location? {
        val lm = (mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var networkLoacation: Location? = null
        var gpsLocation: Location? = null
        var finalLoc: Location? = null
        if (isGpsEnabled) if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (isNetworkLocationEnabled) networkLoacation =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (gpsLocation != null && networkLoacation != null) {

            //smaller the number more accurate result will
            return if (gpsLocation.getAccuracy() > networkLoacation.getAccuracy()) networkLoacation.also {
                finalLoc = it
            } else gpsLocation.also { finalLoc = it }
        } else {
            if (gpsLocation != null) {
                return gpsLocation.also { finalLoc = it }
            } else if (networkLoacation != null) {
                return networkLoacation.also { finalLoc = it }
            }
        }
        return finalLoc
    }
}