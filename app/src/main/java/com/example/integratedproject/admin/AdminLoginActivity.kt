package com.example.integratedproject.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.Cursor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper

class AdminLoginActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_login)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        databaseHelper = DatabaseHelper(this)

        val buttonLogin = findViewById<View>(R.id.buttonLogin)
        val inputPassword = findViewById<View>(R.id.inputPassword)
        val inputEmail = findViewById<View>(R.id.inputEmail)


        buttonLogin.setOnClickListener {
            //TO DO: VERRIFICATIE

            intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
        }
    }
}