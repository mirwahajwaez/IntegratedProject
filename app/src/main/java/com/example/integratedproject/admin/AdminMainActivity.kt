package com.example.integratedproject.admin

import android.app.ActionBar
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper


class AdminMainActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_main)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

        databaseHelper = DatabaseHelper(this)

        val buttonAddStudents = findViewById<Button>(R.id.buttonStudents)
        val buttonAddExams = findViewById<Button>(R.id.buttonExam)
        val buttonResetPassword = findViewById<Button>(R.id.buttonReset)

        val textExamName = findViewById<EditText>(R.id.textExamName)


        buttonAddStudents.setOnClickListener {
            intent = Intent(this, AdminCsvActivity::class.java)
            startActivity(intent)
        }

        buttonResetPassword.setOnClickListener {
            val adminId = intent.getStringExtra("ADMIN_ID")
            intent = Intent(this, AdminResetActivity::class.java)
            intent.putExtra("ADMIN_ID", adminId)
            startActivity(intent)
        }

        buttonAddExams.setOnClickListener {
            databaseHelper!!.addExam(textExamName.text.toString())

            createExamList()
        }
        createExamList()

    }


    private fun createExamList() {
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
                btnEdit.text = getString(R.string.edit)
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