package com.example.integratedproject.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper


class AdminLoginActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_login)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        databaseHelper = DatabaseHelper(this)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val inputPassword = findViewById<EditText>(R.id.inputPassword)
        val inputEmail = findViewById<EditText>(R.id.inputEmail)
        var arrayAdmins: Array<Array<String>>


        //val email = "test@test.be"
        //val pass = "test"
        //databaseHelper!!.addAdmin("test@test.be", "test")


        buttonLogin.setOnClickListener {
            arrayAdmins = databaseHelper!!.allAdmins()

            for(i in arrayAdmins) {
                if (i[0] == inputEmail.text.toString() && i[1] == inputPassword.text.toString()) {
                    intent = Intent(this, AdminMainActivity::class.java)
                    intent.putExtra("ADMIN_ID", i[2])
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "Wrong email and/or password", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
