package com.example.sublearn

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.FirebaseDatabase

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //Identify button to navigate from this activity to login activity on button click
        val back = findViewById<ImageButton>(R.id.buttonBack)
        back.setOnClickListener {
            startActivity(Intent(this@RegisterActivity, LoginActivity::class.java))
            finish()
        }

        //Store reference of database
        val database = FirebaseDatabase.getInstance().reference

        //Identify user inputs as edit views
        val emailTxt = findViewById<View>(R.id.makeEmail) as EditText
        val passTxt = findViewById<View>(R.id.makePassword) as EditText
        val userName = findViewById<EditText>(R.id.createName)


        //Identify button to create account on button click
        findViewById<Button>(R.id.makeAccount).setOnClickListener {

            //If email or password edit views are empty throw an error
            when{
                TextUtils.isEmpty(emailTxt.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(this@RegisterActivity,
                        "Please enter an email.",
                    Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(passTxt.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(this@RegisterActivity,
                        "Please enter a password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                //Create account for users by using stored email and password to Firebase authentication
                else -> {
                    val email: String = emailTxt.text.toString().trim {it <= ' ' }
                    val password: String = passTxt.text.toString().trim{it <= ' '}

                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener({ task ->
                            if (task.isSuccessful) {

                                //Get user ID
                                val firebaseUser: FirebaseUser = task.result!!.user!!

                                //Write user name to Firebase within its appropriate user ID
                                val inputName = userName.text.toString()
                                val id = firebaseUser.uid
                                database.child(id.toString()).setValue(Users(inputName))

                                //Notify when an account has been succesfully created
                                Toast.makeText(
                                    this@RegisterActivity,
                                    "You have successfully created an account",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //Identify button to navigate from this activity to welcome fregment on button click
                                val intent = Intent(this@RegisterActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            } else {
                                Toast.makeText(
                                    this@RegisterActivity,
                                    task.exception!!.message.toString(),
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                }
            }
        }
    }
}