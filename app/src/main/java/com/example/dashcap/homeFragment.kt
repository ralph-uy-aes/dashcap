package com.example.dashcap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class homeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_home, container, false)
        val cam_frag = cameraFragment()
        val safe = v.findViewById<ImageButton>(R.id.ib_alert_safe_box)
        val warn = v.findViewById<ImageButton>(R.id.ib_alert_warn_box)
        val danger = v.findViewById<ImageButton>(R.id.ib_alert_danger_box)
        safe.setOnClickListener() {
            (activity as homeScreen?)!!.replaceFragment(cam_frag)

        }

        warn.setOnClickListener() {
            (activity as homeScreen?)!!.replaceFragment(cam_frag)

        }

        danger.setOnClickListener() {
            (activity as homeScreen?)!!.replaceFragment(cam_frag)
        }
        return v
    }

}