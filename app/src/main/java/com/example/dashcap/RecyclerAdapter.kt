package com.example.dashcap

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(private val recList: ArrayList<RecData>): RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.alert_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return recList.size-1
    }

    override fun onBindViewHolder(holder: RecyclerAdapter.ViewHolder, position: Int) {
        val index = recList[position]

        holder.alert_Type.text = index.Type
        if (index.Color == "Yellow"){
            holder.alert_Image.setImageResource(R.drawable.smiled)
            holder.alert_Color.setCardBackgroundColor(Color.argb(255,238,212,68))
        }
        else if (index.Color == "Orange"){
            holder.alert_Image.setImageResource(R.drawable.question_circle)
            holder.alert_Color.setCardBackgroundColor(Color.argb(255,234,92,31))
        }
        else if (index.Color == "Red"){
            holder.alert_Image.setImageResource(R.drawable.alert_triangle)
            holder.alert_Color.setCardBackgroundColor(Color.argb(255,211,63,73))
        }

    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var alert_Image: ImageView
        var alert_Type: TextView
        var alert_Color: CardView

        init{
            alert_Image = itemView.findViewById(R.id.alertImage)
            alert_Type = itemView.findViewById(R.id.alertType)
            alert_Color = itemView.findViewById(R.id.alertBox)
        }
    }
}