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
import android.util.Log

import android.view.inputmethod.EditorInfo
import android.widget.*
import com.example.integratedproject.admin.AdminQuestionsActivity
import java.util.*


class ChosenQuestionActivity : AppCompatActivity() {
    private lateinit var chosenQuestion: Array<String>
    private lateinit var examId: String
    private lateinit var examName: String
    private lateinit var chosenStudent: String
    private lateinit var answers: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosen_question)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        chosenQuestion = intent.getStringArrayExtra("QUESTION") as Array<String>
        examId = intent.getStringExtra("EXAM_ID").toString()
        examName = intent.getStringExtra("EXAM_NAME").toString()
        chosenStudent = intent.getStringExtra("SELECTED_STUDENT").toString()
        answers = intent.getStringExtra("ANSWERS").toString()

        createQuestion()
    }

    private fun createQuestion() {
        val lm = findViewById<View>(R.id.linearLayoutQuestionToAnswer) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val saveQuestion = findViewById<Button>(R.id.buttonSaveQuestion)


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

                var multipleAnswer = false
                var answer = ""
                val rg = RadioGroup(this) //create the RadioGroup

                if (question[1] == "0") {
                    //radiogroup
                        multipleAnswer = false
                    rg.orientation = RadioGroup.VERTICAL
                    for (option in options) {
                        val rb = RadioButton(this)
                        rb.id = option.hashCode()
                        rb.text = option
                        rg.addView(rb)
                    }
                    lm.addView(rg)
                } else {
                    multipleAnswer = true
                    //checkboxes
                    for (option in options) {
                        val cb = CheckBox(this)
                        cb.id = option.hashCode()
                        cb.text = option
                        lm.addView(cb)
                    }
                }

                rg.setOnCheckedChangeListener { group, checkedId ->
                    val selectedOption: Int = rg.checkedRadioButtonId
                    val radioButton = findViewById<RadioButton>(selectedOption)
                    answer = radioButton.text as String
                }


                saveQuestion.setOnClickListener {
                    var punt = 0
                    val correctAnswer = chosenQuestion[3].split("possibleAnswers;")[1].split(";answers;")[1].split(";")[0]
                    if (!multipleAnswer) {
                        if (answer == correctAnswer) {
                            punt = 1
                        }
                    }
                    var stringAnswer = "vraagid;"
                    stringAnswer += chosenQuestion[0]
                    stringAnswer += ";"
                    stringAnswer += answer
                    stringAnswer += ";"
                    stringAnswer += punt
                    answers += stringAnswer;

                    intent = Intent(this, ChosenExam::class.java)
                    intent.putExtra("EXAM_NAME", examName)
                    intent.putExtra("EXAM_ID", examId)
                    intent.putExtra("SELECTED_STUDENT", chosenStudent)
                    intent.putExtra("ANSWERS", answers)

                    startActivity(intent)
                }

            }
            "2" -> {
                // "Code correct"
                val questionTextView = TextView(this)
                questionTextView.text = question[0]
                questionTextView.layoutParams = params

                val code = question[3]

                val codeToCorrect = TextView(this)
                codeToCorrect.text = code
                codeToCorrect.layoutParams = params


                val answerInput = EditText(this)
                answerInput.isSingleLine = false
                answerInput.imeOptions = EditorInfo.IME_FLAG_NO_ENTER_ACTION
                answerInput.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_FLAG_MULTI_LINE
                answerInput.setLines(5)
                answerInput.isVerticalScrollBarEnabled = true
                answerInput.movementMethod = ScrollingMovementMethod.getInstance()
                answerInput.scrollBarStyle = View.SCROLLBARS_INSIDE_INSET
                answerInput.setText(code)
                answerInput.layoutParams = params

                lm.addView(questionTextView)
                lm.addView(codeToCorrect)
                lm.addView(answerInput)


                saveQuestion.setOnClickListener {
                    var punt = 0
                    val answer = answerInput.text.toString()
                    val correctAnswer = chosenQuestion[3].split("answer;")[1]
                    val caseSensitive = chosenQuestion[3].split(";")[1]
                    if (caseSensitive == "1") {
                        if (answer == correctAnswer) {
                            punt = 1
                        }
                    } else {
                        if (answer.lowercase(Locale.getDefault()) == correctAnswer.lowercase(Locale.getDefault())) {
                            punt = 1
                        }
                    }

                    var stringAnswer = "vraagid;"
                    stringAnswer += chosenQuestion[0]
                    stringAnswer += ";"
                    stringAnswer += answer
                    stringAnswer += ";"
                    stringAnswer += punt
                    answers += stringAnswer;
                    Log.d("TAG", answers)


                    intent = Intent(this, ChosenExam::class.java)
                    intent.putExtra("EXAM_NAME", examName)
                    intent.putExtra("EXAM_ID", examId)
                    intent.putExtra("SELECTED_STUDENT", chosenStudent)
                    intent.putExtra("ANSWERS", answers)

                    startActivity(intent)
                }
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

                saveQuestion.setOnClickListener {
                    val answer = answerInput.text.toString()

                    var stringAnswer = "vraagid;"
                    stringAnswer += chosenQuestion[0]
                    stringAnswer += ";"
                    stringAnswer += answer
                    stringAnswer += ";"
                    stringAnswer += 0
                    answers += stringAnswer;


                    intent = Intent(this, ChosenExam::class.java)
                    intent.putExtra("EXAM_NAME", examName)
                    intent.putExtra("EXAM_ID", examId)
                    intent.putExtra("SELECTED_STUDENT", chosenStudent)
                    intent.putExtra("ANSWERS", answers)

                    startActivity(intent)
                }
            }
        }
    }

}