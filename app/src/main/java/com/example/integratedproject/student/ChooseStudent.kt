package com.example.integratedproject.student


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.integratedproject.R
import android.view.View
import android.widget.*
import com.example.integratedproject.database.DatabaseHelper





class ChooseStudent : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var databaseHelper: DatabaseHelper? = null
    private var students = ArrayList<String>()
    private var check = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_student)
        databaseHelper = DatabaseHelper(this)

        val spinner = findViewById<Spinner>(R.id.spinnerStudents)
        spinner.setSelection(0,false)
        spinner.onItemSelectedListener = this

        students = databaseHelper!!.allStudents()
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, students)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        if (++check > 1) {
            intent = Intent(this, StudentMainActivity::class.java)
            intent.putExtra("SELECTED_STUDENT", students[position])
            startActivity(intent)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }




}


