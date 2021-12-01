package com.example.integratedproject.admin

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TableLayout
import android.widget.TextView
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper

class AdminExamResultActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_exam_result)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        databaseHelper = DatabaseHelper(this)

        val textExamName = findViewById<TextView>(R.id.examName)
        textExamName.text = intent.getStringExtra("EXAM_NAME")

        //createResultsList()
    }

    private fun createResultsList() {
        val tb = findViewById<View>(R.id.tableLayout) as TableLayout
        val params: TableLayout.LayoutParams = TableLayout.LayoutParams(
            ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT
        )

        val examsStudents: Array<Array<String>> = databaseHelper!!.allStudentExams(intent.getStringExtra("EXAM_ID")!!.toInt())

        if (examsStudents.isNotEmpty()) {
            for (exam in examsStudents) {

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
                    intent = Intent(this, AdminExamResultActivity::class.java)
                    intent.putExtra("EXAM_ID", exam[0])
                    intent.putExtra("EXAM_NAME", exam[1])
                    startActivity(intent)
                }

            }
        }
    }
}