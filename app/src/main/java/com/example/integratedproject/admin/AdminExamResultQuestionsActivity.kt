package com.example.integratedproject.admin

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper
import com.example.integratedproject.student.ChosenQuestionActivity

class AdminExamResultQuestionsActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null
    private lateinit var studentExam: Array<String>
    private lateinit var examName: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_exam_result_questions)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        databaseHelper = DatabaseHelper(this)

        examName = intent.getStringExtra("EXAM_NAME").toString()
        studentExam = intent.getStringArrayExtra("STUDENTEXAM") as Array<String>

        val examenNaam = findViewById<TextView>(R.id.textViewExamen)
        examenNaam.text = examName

        createQuestionList()
    }


    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearLayoutQuestionsExam) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val questions: Array<Array<String>> = databaseHelper!!.allQuestions(studentExam[1])

        if(questions.isNotEmpty()) {
            for (question in questions) {
                val ll = LinearLayout(this)
                ll.orientation = LinearLayout.HORIZONTAL

                val questionSplitted = question[3].split(';')

                val btn = Button(this)
                btn.text = questionSplitted[0]
                btn.layoutParams = params

                btn.setOnClickListener {
                    intent = Intent(this, AdminViewQuestionActivity::class.java)
                    intent.putExtra("QUESTION", question)
                    intent.putExtra("STUDENTEXAM", studentExam)
                    intent.putExtra("EXAM_NAME", examName)

                    startActivity(intent)
                }

                val typeText = TextView(this)
                typeText.layoutParams = params
                when(question[2]) {
                    "1" -> typeText.text = "Multiple choice"
                    "2" -> typeText.text = "Code correct"
                    "3" -> typeText.text = "Open question"
                }

                val points = TextView(this)
                points.layoutParams = params

                val answeredQuestions = studentExam[5].split("vraagid;")
                for (aq in answeredQuestions) {
                    val splittedQuestion = aq.split(";")
                    if (question[0] == splittedQuestion[0]) {
                        points.text = "  points: " + splittedQuestion[2] + "/1"
                    }
                }

                ll.addView(btn)
                ll.addView(typeText)
                ll.addView(points)
                lm.addView(ll)
            }
        }
    }
}