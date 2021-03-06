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


class AdminViewQuestionActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null
    private lateinit var studentExam: Array<String>
    private lateinit var examName: String
    private lateinit var question: Array<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_view_question)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        databaseHelper = DatabaseHelper(this)

        examName = intent.getStringExtra("EXAM_NAME").toString()
        studentExam = intent.getStringArrayExtra("STUDENTEXAM") as Array<String>
        question = intent.getStringArrayExtra("QUESTION") as Array<String>

        createQuestionList()
    }

    private fun createQuestionList() {
        val lm = findViewById<View>(R.id.linearLayoutQuestions) as LinearLayout
        val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )
        val savePoints = findViewById<Button>(R.id.buttonSaveQuestion)

        val type = question[2]


        val ll = LinearLayout(this)
        ll.orientation = LinearLayout.HORIZONTAL

        val splittedStudentAnswers = studentExam[5].split("vraagid;")


        if (type == "1") {
            lm.removeAllViews()

            val textQuestion = TextView(this)
            textQuestion.text = question[3].split(";")[0]
            textQuestion.layoutParams = params

            val textAnswer = TextView(this)
            textAnswer.text = "correct answer: " + question[3].split(";answers;")[1].split(";")[0]
            textAnswer.layoutParams = params

            val textAnswerStudent = TextView(this)
            textAnswerStudent.text = getString(R.string.dna)
            textAnswerStudent.layoutParams = params

            val inputPoints = EditText(this)
            inputPoints.id = 998-1
            inputPoints.layoutParams = params
            inputPoints.hint = "Enter the points here"

            for (aq in splittedStudentAnswers) {
                val splittedQuestion = aq.split(";")
                if (question[0] == splittedQuestion[0]) {
                    inputPoints.setText(splittedQuestion[2])
                    textAnswerStudent.text = "students' answer: "  + splittedQuestion[1]
                }
            }


            lm.addView(textQuestion)
            lm.addView(textAnswer)
            lm.addView(textAnswerStudent)
            lm.addView(inputPoints)

            savePoints.setOnClickListener {
                var solutionString = ""
                for (aq in splittedStudentAnswers) {
                    val splittedQuestion = aq.split(";")
                    if (question[0] == splittedQuestion[0]) {
                        solutionString += "vraagid;"
                        solutionString += splittedQuestion[0]
                        solutionString += ";"
                        solutionString += splittedQuestion[1]
                        solutionString += ";"
                        solutionString += inputPoints.text
                    } else {
                        solutionString += "vraagid;" + aq
                    }
                }

                databaseHelper!!.updateStudentExams(studentExam[5], solutionString)


                studentExam[5] = solutionString

                intent = Intent(this, AdminExamResultQuestionsActivity::class.java)
                intent.putExtra("EXAM_NAME", examName)
                intent.putExtra("STUDENTEXAM", studentExam)

                Toast.makeText(this, "Points updated", Toast.LENGTH_LONG).show()

                startActivity(intent)
            }


        }

        if (type == "2") {
            lm.removeAllViews()

            val textQuestion = TextView(this)
            textQuestion.text = question[3].split(";")[0]
            textQuestion.layoutParams = params

            val textAnswer = TextView(this)
            textAnswer.text = "correct answer: " + question[3].split(";answer;")[1].split(";")[0]
            textAnswer.layoutParams = params

            val textAnswerStudent = TextView(this)
            textAnswerStudent.text = getString(R.string.dna)
            textAnswerStudent.layoutParams = params

            val inputPoints = EditText(this)
            inputPoints.id = 998-1
            inputPoints.layoutParams = params
            inputPoints.hint = "Enter the points here"

            for (aq in splittedStudentAnswers) {
                val splittedQuestion = aq.split(";")

                if (question[0] == splittedQuestion[0]) {
                    inputPoints.setText(splittedQuestion[2])
                    textAnswerStudent.text = "students' answer: "  + splittedQuestion[1]
                }
            }


            lm.addView(textQuestion)
            lm.addView(textAnswer)
            lm.addView(textAnswerStudent)
            lm.addView(inputPoints)

            savePoints.setOnClickListener {
                var solutionString = ""
                for (aq in splittedStudentAnswers) {
                    val splittedQuestion = aq.split(";")

                    if (question[0] == splittedQuestion[0]) {
                        solutionString += "vraagid;"
                        solutionString += splittedQuestion[0]
                        solutionString += ";"
                        solutionString += splittedQuestion[1]
                        solutionString += ";"
                        solutionString += inputPoints.text
                    } else {
                        solutionString += "vraagid;" + aq
                    }
                }

                databaseHelper!!.updateStudentExams(studentExam[5], solutionString)

                studentExam[5] = solutionString

                intent = Intent(this, AdminExamResultQuestionsActivity::class.java)
                intent.putExtra("EXAM_NAME", examName)
                intent.putExtra("STUDENTEXAM", studentExam)

                Toast.makeText(this, "Points updated", Toast.LENGTH_LONG).show()

                startActivity(intent)
            }
        }

        if (type == "3") {
            lm.removeAllViews()

            val textQuestion = TextView(this)
            textQuestion.text = question[3]
            textQuestion.layoutParams = params


            val textAnswerStudent = TextView(this)
            textAnswerStudent.text = getString(R.string.dna)
            textAnswerStudent.layoutParams = params

            val inputPoints = EditText(this)
            inputPoints.id = 998-1
            inputPoints.layoutParams = params
            inputPoints.hint = "Enter the points here"

            for (aq in splittedStudentAnswers) {
                val splittedQuestion = aq.split(";")

                if (question[0] == splittedQuestion[0]) {
                    inputPoints.setText(splittedQuestion[2])
                    textAnswerStudent.text = "students' answer: "  + splittedQuestion[1]
                }
            }


            lm.addView(textQuestion)
            lm.addView(textAnswerStudent)
            lm.addView(inputPoints)

            savePoints.setOnClickListener {
                var solutionString = ""
                for (aq in splittedStudentAnswers) {
                    val splittedQuestion = aq.split(";")


                    if (question[0] == splittedQuestion[0]) {
                        solutionString += "vraagid;"
                        solutionString += splittedQuestion[0]
                        solutionString += ";"
                        solutionString += splittedQuestion[1]
                        solutionString += ";"
                        solutionString += inputPoints.text
                    } else {
                        solutionString += "vraagid;" + aq
                    }
                }

                databaseHelper!!.updateStudentExams(studentExam[5], solutionString)

                studentExam[5] = solutionString

                intent = Intent(this, AdminExamResultQuestionsActivity::class.java)
                intent.putExtra("EXAM_NAME", examName)
                intent.putExtra("STUDENTEXAM", studentExam)

                Toast.makeText(this, "Points updated", Toast.LENGTH_LONG).show()

                startActivity(intent)
            }
        }

    }
}