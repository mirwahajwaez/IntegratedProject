package com.example.integratedproject.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.integratedproject.R
import com.example.integratedproject.student.StudentMainActivity

class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        val buttonAddStudents = findViewById<Button>(R.id.buttonStudents)
        val buttonViewExams = findViewById<Button>(R.id.buttonExam)

        buttonAddStudents.setOnClickListener {
            intent = Intent(this, AdminCsvActivity::class.java)
            startActivity(intent)
        }

        buttonViewExams.setOnClickListener {
            intent = Intent(this, AdminViewExamActivity::class.java)
            startActivity(intent)
        }


    }
}