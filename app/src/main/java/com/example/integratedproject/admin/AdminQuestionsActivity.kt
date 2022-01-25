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
            val examName = intent.getStringExtra("EXAM_NAME")

            intent = Intent(this, AdminAddQuestionActivity::class.java)
            intent.putExtra("EXAM_ID", examId)
            intent.putExtra("EXAM_NAME", examName)

            startActivity(intent)
        }

        createQuestionList()
    }

    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearLayoutQuestions) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(150,0,0,0)

        if (allQuestions.isNotEmpty()) {
            for (question in allQuestions) {
                val ll = LinearLayout(this)
                ll.orientation = LinearLayout.HORIZONTAL
                val type = question[2]
                val questionArray = question[3].split(';')

                val questionText = TextView(this)
                questionText.layoutParams = params
                questionText.text = "Question: ${questionArray[0]}"

                val typeText = TextView(this)
                typeText.layoutParams = params
                when(type) {
                    "1" -> typeText.text = "Multiple choice"
                    "2" -> typeText.text = "Code correct"
                    "3" -> typeText.text = "Open question"
                }

                val buttonEdit = Button(this)
                val examId = intent.getStringExtra("EXAM_ID")
                val examName = intent.getStringExtra("EXAM_NAME")
                buttonEdit.layoutParams = params
                buttonEdit.text = "Edit"
                buttonEdit.setOnClickListener {
                    intent = Intent(this, AdminEditQuestionActivity::class.java)
                    intent.putExtra("QUESTION", question)
                    intent.putExtra("EXAM_ID", examId)
                    intent.putExtra("EXAM_NAME", examName)
                    startActivity(intent)
                }



                ll.addView(questionText)
                ll.addView(typeText)
                ll.addView(buttonEdit)
                lm.addView(ll)
            }
        }
    }
}