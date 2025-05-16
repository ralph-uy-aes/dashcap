package com.example.dashcap

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomnavigation.BottomNavigationView


class homeScreen : AppCompatActivity() {

    lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    lateinit var locationRequest: LocationRequest

    private var PERMISSION_ID = 1000
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home_screen)

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        //Fragment variables
        val homeFrag = homeFragment()
        val mapFrag = mapFragment()
        val camFrag = cameraFragment()
        val userFrag = userFragment()

        //bottom nav variable
        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        //default fragment
        replaceFragment(homeFrag)

        //logic for fragment switch
        bottomNavView.setOnItemSelectedListener{
            when(it.itemId) {
                R.id.ic_home -> replaceFragment(homeFrag)
                R.id.ic_locate -> replaceFragment(mapFrag)
                R.id.ic_camera -> replaceFragment(camFrag)
                R.id.ic_account -> replaceFragment(userFrag)
            }
            true
        }
        //Removes default tint to override with new image in menu xml and selector xmls in drawable
        bottomNavView.setItemIconTintList(null)

    }
    //replaceFragment function
    fun replaceFragment(fragment: Fragment){
        if (fragment!=null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.frame_layout, fragment)
            transaction.commit()
        }
    }

    fun CheckPermission():Boolean{
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager. PERMISSION_GRANTED
            || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            return true}
    return false
    }

    fun RequestPermission(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION), PERMISSION_ID)
    }

    fun isLocationEnabled():Boolean{
        var locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }


}