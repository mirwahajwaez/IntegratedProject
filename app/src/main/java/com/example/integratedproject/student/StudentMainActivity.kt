package com.example.integratedproject.student

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import com.example.integratedproject.R
import com.example.integratedproject.admin.AdminExamResultActivity
import com.example.integratedproject.admin.AdminQuestionsActivity
import com.example.integratedproject.database.DatabaseHelper

class StudentMainActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.student_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        databaseHelper = DatabaseHelper(this)


        createExamList()
    }


    private fun createExamList() {
        val lm = findViewById<View>(R.id.linearLayoutChooseExam) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val exams: Array<Array<String>> = databaseHelper!!.allExams()

        if(exams.isNotEmpty()) {
            for (exam in exams) {
                val ll = LinearLayout(this)
                ll.orientation = LinearLayout.HORIZONTAL

                val btn = Button(this)
                btn.id = exam.hashCode() + 1
                btn.text = exam[1]
                btn.layoutParams = params

                btn.setOnClickListener {
                    val chosenStudent = intent.getStringExtra("SELECTED_STUDENT")
                    intent = Intent(this, ChosenExam::class.java)
                    intent.putExtra("EXAM_ID", exam[0])
                    intent.putExtra("EXAM_NAME", exam[1])
                    intent.putExtra("SELECTED_STUDENT", chosenStudent)

                    startActivity(intent)
                }



                ll.addView(btn)

                lm.addView(ll)
            }
        }
    }
}