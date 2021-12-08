package com.example.integratedproject.student

import android.Manifest
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.integratedproject.R
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.*
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.example.integratedproject.admin.AdminMainActivity
import com.example.integratedproject.database.DatabaseHelper
import android.location.LocationManager

import android.content.pm.PackageManager
import android.location.Location

import androidx.core.app.ActivityCompat





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
            val location = getLocationWithCheckNetworkAndGPS(this).toString()
            val longitude = location.split(',')[0].split(' ')[1]
            val latitude = location.split(',')[1].split(' ')[0]
            Toast.makeText(this, "$longitude", Toast.LENGTH_LONG).show()
            Toast.makeText(this, "$latitude", Toast.LENGTH_LONG).show()
            startActivity(intent)
        }

    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("Not yet implemented")
    }

    fun getLocationWithCheckNetworkAndGPS(mContext: Context): Location? {
        val lm = (mContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        val isGpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkLocationEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        var networkLoacation: Location? = null
        var gpsLocation: Location? = null
        var finalLoc: Location? = null
        if (isGpsEnabled) if (ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                mContext,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return null
        }
        gpsLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        if (isNetworkLocationEnabled) networkLoacation =
            lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (gpsLocation != null && networkLoacation != null) {

            //smaller the number more accurate result will
            return if (gpsLocation.getAccuracy() > networkLoacation.getAccuracy()) networkLoacation.also {
                finalLoc = it
            } else gpsLocation.also { finalLoc = it }
        } else {
            if (gpsLocation != null) {
                return gpsLocation.also { finalLoc = it }
            } else if (networkLoacation != null) {
                return networkLoacation.also { finalLoc = it }
            }
        }
        return finalLoc
    }


}


