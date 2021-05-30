package com.example.sublearn

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.jem.rubberpicker.RubberSeekBar

class RecordActivity : AppCompatActivity() {

    private lateinit var dbref : DatabaseReference
    private lateinit var userRecyclerview : RecyclerView
    private lateinit var userArrayList : ArrayList<Safety>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)

        //Store instance of database
        val database = FirebaseDatabase.getInstance().reference

        //Identify button to navigate from this activity to welcome fragment on button click
        findViewById<ImageButton>(R.id.buttonRecord).setOnClickListener {
            val intent =
                Intent(this@RecordActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Creating the RubberSeekBar and display its values
        val rubberSeekBar = findViewById<RubberSeekBar>(R.id.rubberSeek)
        val currentValue = rubberSeekBar.getCurrentValue()
        rubberSeekBar.setCurrentValue(currentValue + 5)
        rubberSeekBar.setOnRubberSeekBarChangeListener(object :
            RubberSeekBar.OnRubberSeekBarChangeListener {
            override fun onProgressChanged(seekBar: RubberSeekBar, value: Int, fromUser: Boolean) {
                val rubberText = findViewById<TextView>(R.id.rubberValue)
                rubberText.text = String.format(resources.getString(R.string.rubberText1), value)

                //Identify button to store edit view values to firebase on button click
                findViewById<Button>(R.id.buttonRecord2).setOnClickListener {

                    //Identify user inputs and convert to string
                    val enteredSuburb = findViewById<EditText>(R.id.inputRecord)
                    val recordedSuburb = enteredSuburb.text.toString()
                    val recordedValue = value.toString()

                    //Store a reference of the users unique id to use as key in database
                    val user = FirebaseAuth.getInstance().currentUser!!.uid
                    database.child(user.toString()).child("data").push().setValue(RecordData(recordedSuburb, recordedValue))

                    //Remove users suburb input from edit view
                    enteredSuburb.setText("")

                }
            }
            override fun onStartTrackingTouch(seekBar: RubberSeekBar) {}
            override fun onStopTrackingTouch(seekBar: RubberSeekBar) {}
        })



        //Display stored data in recycler view
        userRecyclerview = findViewById(R.id.userList)
        userRecyclerview.layoutManager = LinearLayoutManager(this)
        userRecyclerview.setHasFixedSize(true)

        userArrayList = arrayListOf<Safety>()
        getUserData()
    }

    private fun getUserData(){
        val user = FirebaseAuth.getInstance().currentUser!!.uid
        dbref = FirebaseDatabase.getInstance().getReference(user).child("data")

        dbref.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                for (userSnapshot in snapshot.children){
                    val user = userSnapshot.getValue(Safety::class.java)
                    userArrayList.add(user!!)
                }
                userRecyclerview.adapter = myAdapater(userArrayList)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}