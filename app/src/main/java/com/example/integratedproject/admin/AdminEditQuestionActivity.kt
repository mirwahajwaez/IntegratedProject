package com.example.integratedproject.admin

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminEditQuestionActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null
    val db = Firebase.firestore


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_edit_question)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        databaseHelper = DatabaseHelper(this)

        createQuestionList()
    }


    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearLayoutQuestions) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )
        val saveQuestion = findViewById<Button>(R.id.buttonSaveQuestion)

        val question = intent.getStringArrayExtra("QUESTION")
        val type = question?.get(2)


        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL



        if (type == "1") {
            lm.removeAllViews()
            ll.removeAllViews()
            val textNewQuestion = TextView(this)
            textNewQuestion.text = "Your question: "
            textNewQuestion.layoutParams = params

            val paramsButton: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
            )
            paramsButton.setMargins(100,0,0,0)

            /*
            val checkBoxMultipleAnswer = CheckBox(this)
            checkBoxMultipleAnswer.text = "Multiple answers"
            checkBoxMultipleAnswer.layoutParams = paramsButton*/

            val addNewOption = Button(this)
            addNewOption.text = "Add option"
            addNewOption.layoutParams = paramsButton


            ll.addView(textNewQuestion)
            //ll.addView(checkBoxMultipleAnswer)
            ll.addView(addNewOption)


            val inputNewQuestion = EditText(this)
            inputNewQuestion.id = 999-1
            inputNewQuestion.layoutParams = params
            inputNewQuestion.setText(question[3].split(";")[0])
            inputNewQuestion.hint = "Enter your question here"

            val textAnswers = TextView(this)
            textAnswers.text = "Correct answer(s): "
            textAnswers.layoutParams = params

            val inputAnswers = EditText(this)
            inputAnswers.id = 998-1
            inputAnswers.layoutParams = params
            inputAnswers.setText(question[3].split(";answers;")[1])
            inputAnswers.hint = "Enter the answer(s) here, seperated by ,"

            lm.addView(ll)
            lm.addView(inputNewQuestion)
            lm.addView(textAnswers)
            lm.addView(inputAnswers)

            var counter = 0

            val possibleAnswers = question[3].split(";possibleAnswers;")[1].split(";answers;")[0].split(";")
            for (answer in possibleAnswers) {
                counter++
                val textNewOption = TextView(this)
                textNewOption.text = "Option $counter:"
                textNewOption.layoutParams = params

                val inputNewOption = EditText(this)
                inputNewOption.id = counter
                inputNewOption.layoutParams = params
                inputNewOption.setText(answer)
                inputNewOption.hint = "Enter your option here"

                lm.addView(textNewOption)
                lm.addView(inputNewOption)
            }

            addNewOption.setOnClickListener {
                counter++
                val textNewOption = TextView(this)
                textNewOption.text = "Option $counter:"
                textNewOption.layoutParams = params

                val inputNewOption = EditText(this)
                inputNewOption.id = counter
                inputNewOption.layoutParams = params
                inputNewOption.hint = "Enter your option here"

                lm.addView(textNewOption)
                lm.addView(inputNewOption)
            }

            saveQuestion.setOnClickListener {
                val listAnswers = inputAnswers.text.toString().split(',')
                var solutionString = ""
                solutionString += findViewById<EditText>(999-1).text.toString()
                solutionString += ";"
                solutionString += "0;"
                /*if (checkBoxMultipleAnswer.isChecked) {
                    "1;"
                } else {
                    "0;"
                }*/
                solutionString += "possibleAnswers;"
                for (j in 1..counter) {
                    solutionString += findViewById<EditText>(j).text.toString()
                    solutionString += ";"
                }
                solutionString += "answers;"
                for (i in listAnswers) {
                    solutionString += i
                    solutionString += ";"
                }

                val id = intent.getStringExtra("EXAM_ID")
                val name = intent.getStringExtra("EXAM_NAME")

                if (id != null) {
                    databaseHelper!!.updateQuestion(question[3], solutionString)
                }
                intent = Intent(this, AdminQuestionsActivity::class.java)
                intent.putExtra("EXAM_ID", id)
                intent.putExtra("EXAM_NAME", name)

                Toast.makeText(this, "Question updated", Toast.LENGTH_LONG).show()


                startActivity(intent)
            }


        }

        if (type == "2") {
            lm.removeAllViews()
            ll.removeAllViews()

            val textNewQuestion = TextView(this)
            textNewQuestion.text = "Your question: "
            textNewQuestion.layoutParams = params

            val paramsButton: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
            )
            paramsButton.setMargins(300,0,0,0)

            val checkBoxCaseSensitive = CheckBox(this)
            checkBoxCaseSensitive.text = "Case sensitive?"
            checkBoxCaseSensitive.layoutParams = paramsButton

            ll.addView(textNewQuestion)
            ll.addView(checkBoxCaseSensitive)

            val inputNewQuestion = EditText(this)
            inputNewQuestion.id = 999-1
            inputNewQuestion.layoutParams = params
            inputNewQuestion.setText(question[3].split(";")[0])
            inputNewQuestion.hint = "Enter your question here"

            val textCodeToCorrect = TextView(this)
            textCodeToCorrect.text = "Your code to correct: "
            textCodeToCorrect.layoutParams = params

            val inputNewCode = EditText(this)
            inputNewCode.id = 998-1
            inputNewCode.layoutParams = params
            inputNewCode.setText(question[3].split(";")[3])
            inputNewCode.hint = "Enter your code here"

            val textAnswers = TextView(this)
            textAnswers.text = "Correct code: "
            textAnswers.layoutParams = params

            val inputAnswers = EditText(this)
            inputAnswers.id = 997-1
            inputAnswers.layoutParams = params
            inputAnswers.setText(question[3].split(";")[5])
            inputAnswers.hint = "Enter the correct code here"

            lm.addView(ll)
            lm.addView(inputNewQuestion)
            lm.addView(textCodeToCorrect)
            lm.addView(inputNewCode)
            lm.addView(textAnswers)
            lm.addView(inputAnswers)

            saveQuestion.setOnClickListener {
                var solutionString = ""
                solutionString += findViewById<EditText>(999-1).text.toString()
                solutionString += ";"
                solutionString += if (checkBoxCaseSensitive.isChecked) {
                    "1;"
                } else {
                    "0;"
                }
                solutionString += "code;"
                solutionString += findViewById<EditText>(998-1).text.toString()
                solutionString += ";answer;"
                solutionString += findViewById<EditText>(997-1).text.toString()


                val id = intent.getStringExtra("EXAM_ID")
                val name = intent.getStringExtra("EXAM_NAME")

                if (id != null) {
                    databaseHelper!!.updateQuestion(question[3], solutionString)
                }
                intent = Intent(this, AdminQuestionsActivity::class.java)
                intent.putExtra("EXAM_ID", id)
                intent.putExtra("EXAM_NAME", name)

                Toast.makeText(this, "Question updated", Toast.LENGTH_LONG).show()


                startActivity(intent)
            }
        }

        if (type == "3") {
            lm.removeAllViews()
            ll.removeAllViews()

            val textNewQuestion = TextView(this)
            textNewQuestion.text = "Your question: "
            textNewQuestion.layoutParams = params

            ll.addView(textNewQuestion)

            val inputNewQuestion = EditText(this)
            inputNewQuestion.id = 999-1
            inputNewQuestion.layoutParams = params
            inputNewQuestion.setText(question[3])
            inputNewQuestion.hint = "Enter your question here"

            lm.addView(ll)
            lm.addView(inputNewQuestion)

            saveQuestion.setOnClickListener {
                var solutionString = ""
                solutionString += findViewById<EditText>(999-1).text.toString()
                val id = intent.getStringExtra("EXAM_ID")
                val name = intent.getStringExtra("EXAM_NAME")
                if (id != null) {
                    databaseHelper!!.updateQuestion(question[3], solutionString)
                    db.collection("questions").document(name.toString()).update("solution", solutionString)
                }
                intent = Intent(this, AdminQuestionsActivity::class.java)
                intent.putExtra("EXAM_ID", id)
                intent.putExtra("EXAM_NAME", name)


                Toast.makeText(this, "Question added", Toast.LENGTH_LONG).show()



                startActivity(intent)
            }



        }

    }
}