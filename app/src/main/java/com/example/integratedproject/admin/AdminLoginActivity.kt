package com.example.integratedproject.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.example.integratedproject.R
import com.example.integratedproject.student.StudentMainActivity

class AdminLoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_login)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        val password = "admin"
        val email = "admin@test.be"

        val txtEmail = findViewById<TextView>(R.id.editTextTextEmailAddress)
        val txtPassword = findViewById<TextView>(R.id.editTextTextPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)


        btnLogin.setOnClickListener {

            if (txtEmail.text.toString() != email || txtPassword.text.toString() != password) {
                Toast.makeText(applicationContext, "Wrong email and/or password!", Toast.LENGTH_LONG).show()
            }

            else{
                    intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
            }

        }



    }
}