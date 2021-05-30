package com.example.sublearn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class SafetyTipsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_safety_tips, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        //Identify button to navigate from this activity to welcome fragment on button click
        view.findViewById<ImageButton>(R.id.buttonSafety).setOnClickListener {
            findNavController().navigate(R.id.action_FifthFragment_to_FirstFragment)
        }
    }
}