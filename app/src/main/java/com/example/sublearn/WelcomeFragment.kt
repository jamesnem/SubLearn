package com.example.sublearn

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class WelcomeFragment : Fragment() {
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Retrieve users name from Firebase Database and display on welcome fragment
        val greetingText = view.findViewById<TextView>(R.id.title_greeting)
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        val database = FirebaseDatabase.getInstance()
        val databaseUsers = database.getReference(user)
        val username = databaseUsers.child("inputName")

        //Retrieve users name from Firebase and dislpay in title
        username.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val bop = snapshot.getValue().toString()
                greetingText.text = String.format(resources.getString(R.string.welcome), bop)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

        //Identify buttons to navigate from this fragment to other activities and fragments on button click
        view.findViewById<Button>(R.id.buttonSafe).setOnClickListener {
            startActivity(Intent(activity, NearActivity::class.java))
        }
        view.findViewById<Button>(R.id.buttonSearch).setOnClickListener {
            startActivity(Intent(activity, StatisticActivity::class.java))
        }
        view.findViewById<Button>(R.id.buttonRecord).setOnClickListener {
            startActivity(Intent(activity, RecordActivity::class.java))
        }
        view.findViewById<Button>(R.id.buttonTips).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_FifthFragment)
        }
        view.findViewById<ImageButton>(R.id.buttonHometoSettings).setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SixthFragment)
        }
    }
}
