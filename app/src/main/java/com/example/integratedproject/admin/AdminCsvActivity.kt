package com.example.integratedproject.admin

import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.integratedproject.R
import com.example.integratedproject.database.DatabaseHelper

class AdminCsvActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_csv)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        databaseHelper = DatabaseHelper(this)

        val buttonImport = findViewById<View>(R.id.buttonAdd)
        val csvTextField = findViewById<View>(R.id.textCsvStudents)

        buttonImport.setOnClickListener {
            val csvData = csvTextField!!.toString()
            if(csvData.isNotEmpty()) {
               val studentsData = csvData.split(",").toTypedArray()
                for (i in studentsData) databaseHelper!!.addStudent(i)
               Toast.makeText(this, "Students stored!", Toast.LENGTH_LONG).show()
            }

            intent = Intent(this, AdminMainActivity::class.java)
            startActivity(intent)
        }

    }
}