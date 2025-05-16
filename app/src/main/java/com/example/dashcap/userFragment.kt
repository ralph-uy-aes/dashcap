package com.example.dashcap

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_splash_screen.*
import kotlinx.android.synthetic.main.fragment_user.*

class userFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v =  inflater.inflate(R.layout.fragment_user, container, false)
        val num1 = v.findViewById<EditText>(R.id.phone1)
        val num2 = v.findViewById<EditText>(R.id.phone2)
        val num3 = v.findViewById<EditText>(R.id.phone3)
        val btn1 = v.findViewById<Button>(R.id.call1)
        val btn2 = v.findViewById<Button>(R.id.call2)
        val btn3 = v.findViewById<Button>(R.id.call3)
        val image = v.findViewById<ImageButton>(R.id.imageButton2)

        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                android.Manifest.permission.CALL_PHONE
            )
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    android.Manifest.permission.CALL_PHONE
                ),
                1000
            )
        }
        btn1.setOnClickListener(){
            val phonenum1 = num1.getText().toString()
            val intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$phonenum1"))
            startActivity(intent)
        }

        btn2.setOnClickListener(){
            val phonenum2 = num2.getText().toString()
            val intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$phonenum2"))
            startActivity(intent)

        }

        btn3.setOnClickListener(){
            val phonenum3 = num3.getText().toString()
            val intent = Intent(Intent.ACTION_CALL)
            intent.setData(Uri.parse("tel:$phonenum3"))
            startActivity(intent)
        }

        image.setOnClickListener(){
            pickFromGallery()
        }

        // Inflate the layout for this fragment
        return v

    }

    private fun pickFromGallery() {
        val i = Intent(Intent.ACTION_PICK)
        i.type = "image/*"
        startActivityForResult(i, 100)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 100 && resultCode == RESULT_OK){
            imageButton2.setImageURI(data?.data)
        }
    }

}