package com.example.integratedproject.admin

import android.content.pm.ActivityInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.integratedproject.R
import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.preference.PreferenceManager
import android.widget.Toast

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import org.osmdroid.config.Configuration

import org.osmdroid.config.Configuration.*
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.ItemizedIconOverlay
import org.osmdroid.views.overlay.ItemizedOverlay
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.OverlayItem
import java.io.File
import java.net.URL

import java.util.ArrayList

class AdminMapviewActivity : AppCompatActivity() {
    private val REQUEST_PERMISSIONS_REQUEST_CODE = 1;
    private var mMapView: MapView? = null

    private var mMyLocationOverlay: ItemizedOverlay<OverlayItem>? = null
    private var items = ArrayList<OverlayItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.admin_mapview)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        mMapView = findViewById(R.id.map)


        val osmConfig = Configuration.getInstance()
        osmConfig.userAgentValue = packageName
        val basePath = File(cacheDir.absolutePath, "osmdroid")
        osmConfig.osmdroidBasePath = basePath
        val tileCache = File(osmConfig.osmdroidBasePath, "tile")
        osmConfig.osmdroidTileCache = tileCache

        getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this));


        if (hasPermissions()) {
            initMap()
        }
        else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION), 100)
        }





    }

    private fun hasPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun initMap() {
        mMapView?.setTileSource(TileSourceFactory.DEFAULT_TILE_SOURCE)

        mMapView?.controller?.setZoom(17.0)

        val latitude = intent.getStringExtra("LATITUDE")
        val longitude = intent.getStringExtra("LONGITUDE")
           val startPoint = latitude?.toDouble()?.let { longitude?.toDouble()?.let { it1 ->
           GeoPoint(it,
               it1
           )}}
        if (startPoint != null) {
            setCenter(startPoint, "Student Location")
        }
    }

    private fun setCenter(geoPoint: GeoPoint, name: String) {
        mMapView?.controller?.setCenter(geoPoint)
        addMarker(geoPoint, name)
    }

    private fun addMarker(geoPoint: GeoPoint, name: String) {
        items.add(OverlayItem(name, name, geoPoint))
        mMyLocationOverlay = ItemizedIconOverlay(items, null, applicationContext)
        mMapView?.overlays?.add(mMyLocationOverlay)
    }

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        val permissionsToRequest = ArrayList<String>();
        var i = 0;
        while (i < grantResults.size) {
            permissionsToRequest.add(permissions[i]);
            i++;
        }
        if (permissionsToRequest.size > 0) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                REQUEST_PERMISSIONS_REQUEST_CODE);
        }
    }


}