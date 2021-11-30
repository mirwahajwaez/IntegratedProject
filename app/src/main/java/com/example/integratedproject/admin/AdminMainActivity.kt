package com.example.integratedproject.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.integratedproject.R
import com.example.integratedproject.student.StudentMainActivity

class AdminMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        val buttonAddStudents = findViewById<View>(R.id.buttonStudents)

        buttonAddStudents.setOnClickListener {
            intent = Intent(this, AdminCsvActivity::class.java)
            startActivity(intent)
        }


    }
}