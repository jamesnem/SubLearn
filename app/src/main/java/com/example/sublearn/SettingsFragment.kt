package com.example.sublearn

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class SettingsFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Identify button to navigate from this activity to welcome fragment on button click
        view.findViewById<ImageButton>(R.id.buttonSettings).setOnClickListener {
            findNavController().navigate(R.id.action_SixthFragment_to_FirstFragment)
        }

        //Identify button to navigate from this fragment to login on button click and sign out of account
        view.findViewById<Button>(R.id.buttonSettings2).setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            startActivity(Intent(activity, LoginActivity::class.java))
            activity?.finish()

        }
    }
}