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
    private var allQuestions: Array<Array<String>> = emptyArray()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_questions)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        databaseHelper = DatabaseHelper(this)


        val buttonAddQuestions = findViewById<Button>(R.id.buttonAddQuestion)
        val questionsTextField = findViewById<TextView>(R.id.textView6)


        allQuestions = databaseHelper!!.allQuestions(intent.getStringExtra("EXAM_ID"))

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

        createQuestionList()
    }

    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearLayoutQuestions) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        if (allQuestions.isNotEmpty()) {
            for (question in allQuestions) {
                val ll = LinearLayout(this)
                ll.orientation = LinearLayout.HORIZONTAL

                val questionText = TextView(this)
                questionText.layoutParams = params
                questionText.text = "Question: ${question[3]}"
                //todo: vraag er uit halen

                val type = TextView(this)
                type.layoutParams = params
                type.text = "Type: ${question[2]}"
                //todo: switch voor type te vinden


                val buttonEdit = Button(this)
                buttonEdit.layoutParams = params
                buttonEdit.text = "Edit"



                ll.addView(questionText)
                ll.addView(type)
                ll.addView(buttonEdit)
                lm.addView(ll)
            }
        }
    }
}