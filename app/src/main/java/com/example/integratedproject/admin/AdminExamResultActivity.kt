package com.example.integratedproject.admin

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.core.view.marginStart
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AdminExamResultActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_exam_result)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        databaseHelper = DatabaseHelper(this)

        val textExamName = findViewById<TextView>(R.id.examName)
        textExamName.text = intent.getStringExtra("EXAM_NAME")


        //databaseHelper!!.addStudentExam(1, 1, 4.475670, 51.230040, "8/10", "Someanswers")
        //databaseHelper!!.addStudentExam(3, 1, 4.475670, 51.230040, "5/10", "Someanswers")

        createResultsList()
    }

    private fun createResultsList() {


        val tb = findViewById<View>(R.id.tableLayout) as TableLayout
        val params: TableRow.LayoutParams = TableRow.LayoutParams(
            ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.MATCH_PARENT
        )
        val params2: TableRow.LayoutParams = TableRow.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val examsStudents: Array<Array<String>> = databaseHelper!!.allStudentExams(intent.getStringExtra("EXAM_ID")!!.toInt())


        if (examsStudents.isNotEmpty()) {
            for (exam in examsStudents) {
                //val snummer: ArrayList<String> = databaseHelper!!.getStudent(exam[0])

                val tr = TableRow(this)
                tr.layoutParams = params
                //2 text views, into tablerow into table layout

                val textSnummer = TextView(this)
                textSnummer.text = exam[0]
                textSnummer.layoutParams = params2


                val textPoints = TextView(this)
                textPoints.text = exam[4]
                textPoints.layoutParams = params2
                textPoints.layout(150,0,0,0)

                val btn = Button(this)
                btn.text = "Answers"
                btn.layoutParams = params2

                btn.setOnClickListener {
                    val examName = intent.getStringExtra("EXAM_NAME")

                    intent = Intent(this, AdminExamResultQuestionsActivity::class.java)
                    intent.putExtra("STUDENTEXAM", exam)
                    intent.putExtra("EXAM_NAME", examName)


                    startActivity(intent)
                }

                val btnEdit = Button(this)
                btnEdit.text = "Mapview"
                btnEdit.layoutParams = params2

                btnEdit.setOnClickListener {
                    intent = Intent(this, AdminMapviewActivity::class.java)
                    intent.putExtra("LATITUDE", exam[2])
                    intent.putExtra("LONGITUDE", exam[3])

                    startActivity(intent)
                }

                tr.addView(textSnummer)
                tr.addView(btn)
                tr.addView(btnEdit)
                tr.addView(textPoints)
                tb.addView(tr)
            }
        }
    }
}