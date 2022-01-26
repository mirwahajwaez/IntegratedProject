package com.example.integratedproject.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class AdminResetActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_reset)

        databaseHelper = DatabaseHelper(this)

        val buttonResetPassword = findViewById<Button>(R.id.buttonResetPw)
        val textNewPassword = findViewById<EditText>(R.id.textNewPassword)
        val textNewPassword2 = findViewById<EditText>(R.id.textNewPassword2)

        buttonResetPassword.setOnClickListener {
            if (textNewPassword.text.toString() == textNewPassword2.text.toString()) {
                val adminId = intent.getStringExtra("ADMIN_ID")
                val admins: ArrayList<String>? = adminId?.let { it1 ->
                    databaseHelper!!.getOneAdmin(
                        it1
                    )
                }
                if (admins!![1] != textNewPassword.text.toString()) {
                    //update pw in db
                        databaseHelper!!.updateAdmin(admins[1],textNewPassword.text.toString())
                    Toast.makeText(this, "Password updated, please login again", Toast.LENGTH_SHORT).show()

                    intent = Intent(this, AdminLoginActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "New password equals current password", Toast.LENGTH_SHORT).show()

                }
            } else {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()

            }

        }


    }
}