package com.example.sublearn

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

//Create an adapter that binds the .csv file data to the recycler view layout
class myAdapater(private val userList : ArrayList<Safety>) : RecyclerView.Adapter<myAdapater.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {

        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.user_item,
        parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        val currentItem = userList[position]
        holder.inputSafety.text = currentItem.inputSafety
        holder.inputSuburb.text = currentItem.inputSuburb
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class MyViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val inputSafety : TextView = itemView.findViewById(R.id.inputSafety)
        val inputSuburb : TextView = itemView.findViewById(R.id.inputSuburb)
    }
}