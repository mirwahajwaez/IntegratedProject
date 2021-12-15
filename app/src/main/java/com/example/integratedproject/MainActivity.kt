package com.example.integratedproject

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.example.integratedproject.admin.AdminLoginActivity
import com.example.integratedproject.student.ChooseStudent
import com.example.integratedproject.student.StudentMainActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main)

        val buttonAdmin = findViewById<View>(R.id.btnAdmin)
        val buttonStudent = findViewById<Button>(R.id.btnStudent)

//        buttonStudent.setOnClickListener {
//            intent = Intent(this, StudentMainActivity::class.java)
//            startActivity(intent)
//        }
        buttonStudent.setOnClickListener {
            intent = Intent(this, ChooseStudent::class.java)
            startActivity(intent)
        }

        buttonAdmin.setOnClickListener {
            intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }
    }
}