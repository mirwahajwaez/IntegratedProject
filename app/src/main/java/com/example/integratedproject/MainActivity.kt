package com.example.integratedproject

import android.content.ContentValues
import android.content.Intent
import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.example.integratedproject.admin.AdminLoginActivity
import com.example.integratedproject.database.DatabaseHelper
import com.example.integratedproject.student.ChooseStudent
import com.example.integratedproject.student.StudentMainActivity
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    private var databaseHelper: DatabaseHelper? = null
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_main)
        databaseHelper = DatabaseHelper(this)

        val buttonAdmin = findViewById<View>(R.id.btnAdmin)
        val buttonStudent = findViewById<Button>(R.id.btnStudent)
        val buttonFirebase = findViewById<Button>(R.id.btnFirebase)


        buttonStudent.setOnClickListener {
            intent = Intent(this, ChooseStudent::class.java)
            startActivity(intent)
        }

        buttonAdmin.setOnClickListener {
            intent = Intent(this, AdminLoginActivity::class.java)
            startActivity(intent)
        }


        buttonFirebase.setOnClickListener {
            val students = databaseHelper!!.allStudents()
            val admins = databaseHelper!!.allAdmins()
            val exams = databaseHelper!!.allExams()
            val questions = databaseHelper!!.getAllQuestions()
            val studentExams = databaseHelper!!.getAllStudentExams()

            for(student in students) {
                val studentToAdd = hashMapOf(
                    "s-number" to student
                )
                db.collection("students").document(student).set(studentToAdd)
            }
            for(admin in admins) {
                val adminToAdd = hashMapOf(
                    "email" to admin[0],
                    "password" to admin[1]
                )
                db.collection("admin").document(admin[2]).set(adminToAdd)
            }
            for(exam in exams) {
                val examToAdd = hashMapOf(
                    "name" to exam[1]
                )
                db.collection("exams").document(exam[0]).set(examToAdd)
            }
            for(question in questions) {
                val questionToAdd = hashMapOf(
                    "examId" to question[1],
                    "type" to question[2],
                    "questionString" to question[3]
                )
                db.collection("questions").document(question[0]).set(questionToAdd)
            }
            for(studentExam in studentExams) {
                val studentExamToAdd = hashMapOf(
                    "s-number" to studentExam[0],
                    "examId" to studentExam[1],
                    "latitude" to studentExam[2],
                    "longitude" to studentExam[3],
                    "counter" to studentExam[4],
                    "answerString" to studentExam[5]
                )
                db.collection("studentExams").document("${studentExam[0]}-${studentExam[1]}").set(studentExamToAdd)
            }

            Toast.makeText(this, "Synced data with firebase", Toast.LENGTH_SHORT).show()

        }
    }
}