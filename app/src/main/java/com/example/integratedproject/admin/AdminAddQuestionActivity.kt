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

class AdminAddQuestionActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_add_question)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        databaseHelper = DatabaseHelper(this)


    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            val checked = view.isChecked

            when (view.getId()) {
                R.id.radioButtonMC ->
                    if (checked) {
                        createQuestionList(0)
                    }
                R.id.radioButtonCC ->
                    if (checked) {
                        createQuestionList(1)
                    }
                R.id.radioButtonOQ ->
                    if (checked) {
                        createQuestionList(2)
                    }
            }
        }
    }

    private fun createQuestionList(type: Int) {
        val lm = findViewById<View>(R.id.linearLayoutQuestion) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )
        val addQuestion = findViewById<Button>(R.id.buttonAddNewQuestion)


        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL



        if (type == 0) {
            lm.removeAllViews()
            ll.removeAllViews()
            val textNewQuestion = TextView(this)
            textNewQuestion.text = "Your question: "
            textNewQuestion.layoutParams = params

            val paramsButton: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
                ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
            )
            paramsButton.setMargins(100,0,0,0)

            val checkBoxMultipleAnswer = CheckBox(this)
            checkBoxMultipleAnswer.text = "Multiple answers"
            checkBoxMultipleAnswer.layoutParams = paramsButton

            val addNewOption = Button(this)
            addNewOption.text = "Add option"
            addNewOption.layoutParams = paramsButton

            ll.addView(textNewQuestion)
            ll.addView(checkBoxMultipleAnswer)
            ll.addView(addNewOption)


            val inputNewQuestion = EditText(this)
            inputNewQuestion.id = 999-1
            inputNewQuestion.layoutParams = params
            inputNewQuestion.hint = "Enter your question here"

            val textAnswers = TextView(this)
            textAnswers.text = "Correct answer(s): "
            textAnswers.layoutParams = params

            val inputAnswers = EditText(this)
            inputAnswers.id = 998-1
            inputAnswers.layoutParams = params
            inputAnswers.hint = "Enter the answer(s) here, seperated by ,"

            lm.addView(ll)
            lm.addView(inputNewQuestion)
            lm.addView(textAnswers)
            lm.addView(inputAnswers)



            var counter = 0

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

            addQuestion.setOnClickListener {
                val listAnswers = inputAnswers.text.toString().split(',')
                var solutionString = ""
                solutionString += findViewById<EditText>(999-1).text.toString()
                solutionString += ";"
                solutionString += if (checkBoxMultipleAnswer.isChecked) {
                    "1;"
                } else {
                    "0;"
                }
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

                //Add question to DB
                intent.getStringExtra("EXAM_ID")?.let { it1 -> databaseHelper!!.addQuestions(it1,1,solutionString) }
                intent = Intent(this, AdminQuestionsActivity::class.java)

                Toast.makeText(this, "Question added", Toast.LENGTH_LONG).show()


                startActivity(intent)
            }


        }

        if (type == 1) {
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
            inputNewQuestion.hint = "Enter your question here"

            val textCodeToCorrect = TextView(this)
            textCodeToCorrect.text = "Your code to correct: "
            textCodeToCorrect.layoutParams = params

            val inputNewCode = EditText(this)
            inputNewCode.id = 998-1
            inputNewCode.layoutParams = params
            inputNewCode.hint = "Enter your code here"

            val textAnswers = TextView(this)
            textAnswers.text = "Correct code: "
            textAnswers.layoutParams = params

            val inputAnswers = EditText(this)
            inputAnswers.id = 997-1
            inputAnswers.layoutParams = params
            inputAnswers.hint = "Enter the correct code here"

            lm.addView(ll)
            lm.addView(inputNewQuestion)
            lm.addView(textCodeToCorrect)
            lm.addView(inputNewCode)
            lm.addView(textAnswers)
            lm.addView(inputAnswers)

            addQuestion.setOnClickListener {
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
                solutionString += "answer;"
                solutionString += findViewById<EditText>(997-1).text.toString()


                intent.getStringExtra("EXAM_ID")?.let { it1 -> databaseHelper!!.addQuestions(it1,2,solutionString) }
                intent = Intent(this, AdminQuestionsActivity::class.java)

                Toast.makeText(this, "Question added", Toast.LENGTH_LONG).show()


                startActivity(intent)
            }
        }

        if (type == 2) {
            lm.removeAllViews()
            ll.removeAllViews()

            val textNewQuestion = TextView(this)
            textNewQuestion.text = "Your question: "
            textNewQuestion.layoutParams = params

            ll.addView(textNewQuestion)

            val inputNewQuestion = EditText(this)
            inputNewQuestion.id = 999-1
            inputNewQuestion.layoutParams = params
            inputNewQuestion.hint = "Enter your question here"

            lm.addView(ll)
            lm.addView(inputNewQuestion)

            addQuestion.setOnClickListener {
                var solutionString = ""
                solutionString += findViewById<EditText>(999-1).text.toString()
                val id = intent.getStringExtra("EXAM_ID")
                val name = intent.getStringExtra("EXAM_NAME")
                if (id != null) {
                    databaseHelper!!.addQuestions(id,3,solutionString)
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