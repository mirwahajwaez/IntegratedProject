package com.example.integratedproject.student

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.InputType
import android.view.View
import com.example.integratedproject.R
import android.text.method.ScrollingMovementMethod

import android.view.inputmethod.EditorInfo
import android.widget.*


class ChosenQuestionActivity : AppCompatActivity() {
    private lateinit var chosenQuestion: Array<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_question)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        chosenQuestion = intent.getStringArrayExtra("QUESTION") as Array<String>


        createQuestion()
    }

    private fun createQuestion() {
        val lm = findViewById<View>(R.id.linearLayoutQuestionToAnswer) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val question = chosenQuestion[3].split(';')

        when(chosenQuestion[2]) {
            "1" -> {
                // "Multiple choice"
                val questionTextView = TextView(this)
                questionTextView.text = question[0]
                questionTextView.layoutParams = params

                lm.addView(questionTextView)

                var options = chosenQuestion[3].split("possibleAnswers;")
                options = options[1].split(";answers;")
                options = options[0].split(';')

                if (question[1] == "0") {
                    //radiogroup
                    val rg = RadioGroup(this) //create the RadioGroup
                    rg.orientation = RadioGroup.VERTICAL
                    for (option in options) {
                        val rb = RadioButton(this)
                        rb.text = option
                        rg.addView(rb)
                    }
                    lm.addView(rg)
                } else {
                    //checkboxes
                }

            }
            "2" -> {
                // "Code correct"
                val questionTextView = TextView(this)
                questionTextView.text = question[0]
                questionTextView.layoutParams = params

                val questionOmittedChars = TextView(this)
                questionOmittedChars.text = "Ignored characters : "
                questionOmittedChars.layoutParams = params

                val codeToCorrect = TextView(this)
                codeToCorrect.text = question[2]
                codeToCorrect.layoutParams = params


                val answerInput = EditText(this)
                answerInput.isSingleLine = false
                answerInput.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                answerInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                answerInput.setLines(5)
                answerInput.isVerticalScrollBarEnabled = true
                answerInput.movementMethod = ScrollingMovementMethod.getInstance()
                answerInput.scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
                answerInput.setText(question[2])
                answerInput.layoutParams = params

                lm.addView(questionTextView)
                lm.addView(questionOmittedChars)
                lm.addView(codeToCorrect)
                lm.addView(answerInput)
            }
            "3" -> {
                // "Open question"
                val questionTextView = TextView(this)
                questionTextView.text = question[0]
                questionTextView.layoutParams = params

                val answerInput = EditText(this)
                answerInput.isSingleLine = false
                answerInput.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                answerInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                answerInput.setLines(5)
                answerInput.isVerticalScrollBarEnabled = true
                answerInput.movementMethod = ScrollingMovementMethod.getInstance()
                answerInput.scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
                answerInput.layoutParams = params

                lm.addView(questionTextView)
                lm.addView(answerInput)
            }
        }
    }

}