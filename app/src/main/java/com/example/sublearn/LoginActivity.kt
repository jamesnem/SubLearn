package com.example.sublearn

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //Identify button to navigate from this activity to register activity on button click
        val create = findViewById<Button>(R.id.buttonCreate)
        create.setOnClickListener {
            startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            finish()
        }

        //Store user values as edit views
        val emailTxt = findViewById<View>(R.id.UserEmail) as EditText
        val passTxt = findViewById<View>(R.id.UserPassword) as EditText

        //Identify button to login to account on button click
        findViewById<Button>(R.id.buttonLogin).setOnClickListener {

            //If email or password fields are blank return an error toast message
            when{
                TextUtils.isEmpty(emailTxt.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(this@LoginActivity,
                        "Please enter an email.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                TextUtils.isEmpty(passTxt.text.toString().trim { it <= ' '}) -> {
                    Toast.makeText(this@LoginActivity,
                        "Please enter a password.",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                else -> {
                    val email: String = emailTxt.text.toString().trim {it <= ' ' }
                    val password: String = passTxt.text.toString().trim{it <= ' '}

                    //Validate whether users email and password matched to any exisitng accounts
                    FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener({ task ->
                            if (task.isSuccessful) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Logged in successfully",
                                    Toast.LENGTH_SHORT
                                ).show()

                                //navigate from this activity to welcome fragment on button click
                                val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                intent.putExtra("userID",
                                    FirebaseAuth.getInstance().currentUser!!.uid
                                )
                                intent.putExtra("email_id", email)
                                startActivity(intent)
                                finish()
                            }

                            //Return an error message
                            else {
                                Toast.makeText(
                                    this@LoginActivity,
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