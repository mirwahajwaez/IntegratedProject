package com.example.integratedproject.admin

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
import com.example.integratedproject.database.DatabaseHelper

class AdminQuestionsActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_questions)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        databaseHelper = DatabaseHelper(this)


        val buttonAddQuestions = findViewById<Button>(R.id.buttonAddQuestion)
        val questionsTextField = findViewById<TextView>(R.id.textView6)


        val allQuestions: Array<Array<String>> = databaseHelper!!.allQuestions(intent.getStringExtra("EXAM_ID"))

        if (allQuestions.isNotEmpty()) {
            questionsTextField.text =
                "Your questions for exam ${intent.getStringExtra("EXAM_NAME")}"
        }


        buttonAddQuestions.setOnClickListener {
            val examId = intent.getStringExtra("EXAM_ID")
            intent = Intent(this, AdminAddQuestionActivity::class.java)
            intent.putExtra("EXAM_ID", examId)
            startActivity(intent)
        }

        //createQuestionList()
    }

    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearMain) as LinearLayout
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
                    intent = Intent(this, AdminExamResultActivity::class.java)
                    intent.putExtra("EXAM_ID", exam[0])
                    intent.putExtra("EXAM_NAME", exam[1])

                    startActivity(intent)
                }

                val btnEdit = Button(this)
                btnEdit.text = "Edit"
                btnEdit.layoutParams = params

                btnEdit.setOnClickListener {
                    intent = Intent(this, AdminQuestionsActivity::class.java)
                    intent.putExtra("EXAM_ID", exam[0])
                    intent.putExtra("EXAM_NAME", exam[1])
                    startActivity(intent)
                }


                ll.addView(btn)
                ll.addView(btnEdit)

                lm.addView(ll)
            }
        }
    }
}