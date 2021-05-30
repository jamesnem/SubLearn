package com.example.sublearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Create an adapter that binds the .csv file data to the recycler view layout
class nearAdapter(private val nearmeRec: List<nearMeList>) : RecyclerView.Adapter<nearAdapter.nearViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): nearViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.nearme,
        parent, false)

        return nearViewHolder(itemView)

    }

    override fun onBindViewHolder(holder: nearViewHolder, position: Int) {

        val currentItem = nearmeRec[position]
        holder.textView1.text = currentItem.text1
        holder.textView2.text = currentItem.text2.toString()
        holder.textView3.text = currentItem.text3.toString()
    }

    override fun getItemCount() = nearmeRec.size

    class nearViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textView1: TextView = itemView.findViewById(R.id.inputNear)
        val textView2: TextView = itemView.findViewById(R.id.inputDistance)
        val textView3: TextView = itemView.findViewById(R.id.inputCrash)
    }
}