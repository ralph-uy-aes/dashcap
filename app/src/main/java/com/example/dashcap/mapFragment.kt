package com.example.dashcap

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class mapFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        // Initialize view
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        val btn1 = view.findViewById<ImageView>(R.id.Kaneohe_box)
        val btn2 = view.findViewById<ImageView>(R.id.MCBH_box)
        val btn3 = view.findViewById<ImageView>(R.id.Kalihi_box)
        val btn4 = view.findViewById<ImageView>(R.id.Kailua_box)
        val btn5 = view.findViewById<ImageView>(R.id.Kapolei_box)
        val btn6 = view.findViewById<ImageView>(R.id.Honolulu_box)
        val btn7 = view.findViewById<ImageView>(R.id.Pearl_City_box)
        val btn8 = view.findViewById<ImageView>(R.id.Waikiki_box)
        val btn9 = view.findViewById<ImageView>(R.id.Wahiawa_box)
        val btn10 = view.findViewById<ImageView>(R.id.Honolulu2_box)
        val btn11 = view.findViewById<ImageView>(R.id.Kahuku_box)


        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.google_map) as SupportMapFragment?

        val Kaneohe = LatLng(21.413340, -157.798880)
        val MCBH = LatLng(21.439800, -157.755200)
        val Kalihi = LatLng(21.344250, -157.870370)
        val Kailua = LatLng(21.396260, -157.739740)
        val Kapolei = LatLng(21.335630, -158.081240)
        val Honolulu = LatLng(21.304160, -157.851320)
        val Pearl_City = LatLng(21.304160, -157.851320)
        val Waikiki = LatLng(21.275410, -157.825540)
        val Wahiawa = LatLng(21.501560, -158.025430)
        val Honolulu2 = LatLng(21.307740, -157.859320)
        val Kahuku = LatLng(21.674820, -157.946340)

        var locationArrayList: ArrayList<LatLng>? = null

        locationArrayList = ArrayList()

        locationArrayList.add(Kaneohe)
        locationArrayList.add(MCBH)
        locationArrayList.add(Kalihi)
        locationArrayList.add(Kailua)
        locationArrayList.add(Kapolei)
        locationArrayList.add(Honolulu)
        locationArrayList.add(Pearl_City)
        locationArrayList.add(Waikiki)
        locationArrayList.add(Wahiawa)
        locationArrayList.add(Honolulu2)
        locationArrayList.add(Kahuku)

        //Once map runs
        supportMapFragment!!.getMapAsync { googleMap ->
            if (ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    requireActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ),
                    1000
                )
            }

            googleMap.setMyLocationEnabled(true)
            googleMap.getUiSettings().setMyLocationButtonEnabled(true)
            googleMap.getUiSettings().setMapToolbarEnabled(true)

            for (i in locationArrayList.indices){
                googleMap.addMarker(MarkerOptions().position(locationArrayList[i]).title("Police Station"))
                googleMap.animateCamera(CameraUpdateFactory.zoomTo(18.0f))
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(locationArrayList.get(i)))
            }
            btn1.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Kaneohe, 16f))
            }
            btn2.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MCBH, 16f))
            }
            btn3.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Kalihi, 16f))
            }
            btn4.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Kailua, 16f))
            }
            btn5.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Kapolei, 16f))
            }
            btn6.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Honolulu, 16f))
            }
            btn7.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Pearl_City, 16f))
            }
            btn8.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Waikiki, 16f))
            }
            btn9.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Wahiawa, 16f))
            }
            btn10.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Honolulu2, 16f))
            }
            btn11.setOnClickListener(){
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(Kahuku, 16f))
            }
            if((activity as homeScreen?)!!.CheckPermission()){
                if((activity as homeScreen?)!!.isLocationEnabled()){
                    if (ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_FINE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                            requireContext(),
                            Manifest.permission.ACCESS_COARSE_LOCATION
                        ) != PackageManager.PERMISSION_GRANTED
                    ) {
                    }
                    (activity as homeScreen?)!!.fusedLocationProviderClient.lastLocation.addOnCompleteListener{ task->
                        var location: Location? = task.result
                        if(location == null) {

                        }else{
                            val userLocation = LatLng(location.latitude, location.longitude)
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 16f))

                        }
                    }
                }else{
                    Toast.makeText(requireContext(), "Location Services Unavailable.", Toast.LENGTH_SHORT).show()
                }
            }else{
                (activity as homeScreen?)!!.RequestPermission()
            }
        }

        // Return view
        return view
        }

    }