package com.example.dashcap

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_camera.*

class cameraFragment : Fragment() {


    //private var layoutManager: RecyclerView.LayoutManager? = null
    //private var adapter: RecyclerView.Adapter<RecyclerAdapter.ViewHolder>? = null
    private lateinit var dbref: DatabaseReference
    private lateinit var recDataArrayList: ArrayList<RecData>
    private lateinit var myRecyclerView:RecyclerView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {


        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_camera, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        //layoutManager = LinearLayoutManager(this.activity)
        //recyclerView.layoutManager = layoutManager
        //adapter = RecyclerAdapter()
        //recyclerView.adapter = adapter

        myRecyclerView = view.findViewById(R.id.recyclerView)
        myRecyclerView.layoutManager = LinearLayoutManager(activity)
        myRecyclerView.setHasFixedSize(true)

        recDataArrayList = arrayListOf<RecData>()
        getRecData()

        val myWebView: WebView = view.findViewById(R.id.webView1)

        myWebView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                url: String
            ): Boolean {
                view.loadUrl(url)
                return true
            }
        }

        myWebView.loadUrl("http://192.168.1.5:5000/")
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.allowContentAccess = true
        myWebView.settings.domStorageEnabled = true
        myWebView.settings.useWideViewPort = true

    }

    private fun getRecData() {
        dbref = FirebaseDatabase.getInstance().getReference("Recognition")

        dbref.addValueEventListener(object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for(recSnapshot in snapshot.children){
                        val rec = recSnapshot.getValue(RecData::class.java)
                         recDataArrayList.add(rec!!)
                    }
                    myRecyclerView.adapter = RecyclerAdapter(recDataArrayList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
            }

        })
    }
}