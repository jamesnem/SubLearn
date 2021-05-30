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
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.InputStream
import java.lang.Math.*

class NearActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_near)

        //Identify button to navigate from this activity to welcome fragment on button click
        findViewById<ImageButton>(R.id.buttonNear).setOnClickListener {
            val intent =
                Intent(this@NearActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Identify button to display suburbs near users suburb on button click
        findViewById<Button>(R.id.buttonNear2).setOnClickListener {

            //Get users input and convert to a string
            val suburb = findViewById<EditText>(R.id.inputNear)
            val userSuburb = suburb.text.toString()
            val returnText = findViewById<TextView>(R.id.valueNear)

            //Create a function that uses the haversine formula and will return a double
            fun haversine(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {

                //Kilometers
                val R = 6372.8
                val val1 = toRadians(lat1)
                val val2 = toRadians(lat2)
                val val3 = toRadians(lat2 - lat1)
                val val4 = toRadians(lon2 - lon1)
                return 2 * R * asin(sqrt(pow(sin(val3 / 2), 2.0) + pow(sin(val4 / 2), 2.0) * cos(val1) * cos(val2)))
            }

            //Read through .csv file and iterate through each line
            //If iterated row index 0 is equal to users input display closest, else return an error
            val inputStream: InputStream = assets.open("crash_data.csv")
            csvReader().open(inputStream) {
                for (row: List<String> in readAllAsSequence()){
                    if (row[0] == userSuburb){

                        //Store iterated index positions as coordinates
                        val currentLong = row[1].toDouble()
                        val currentLat = row[2].toDouble()

                        //Create lists that will be used to stored data
                        val namesList : MutableList<String> = mutableListOf()
                        val statsList : MutableList<Double> = mutableListOf()
                        val crashList : MutableList<Double> = mutableListOf()

                        //Loop through .csv file data again to find distances between users suburb and all suburbs
                        for (coordinates: List<String> in readAllAsSequence()) {
                            val otherLong = coordinates[1].toDouble()
                            val otherLat = coordinates[2].toDouble()
                            val distances = haversine(currentLat, currentLong, otherLat, otherLong)

                            //Append the values to the corresponding lists
                            statsList.add(distances)
                            namesList.add(coordinates[0])
                            crashList.add(coordinates[3].toDouble())
                        }

                        //Use zip to pair the lists together to retain index positions after sorting
                        val matchedList = namesList.zip(statsList)
                        val secMatchedList = matchedList.zip(crashList)
                        val sortedList = secMatchedList.sortedBy { it.first.second }
                        var top5List : MutableList<Pair<Pair<String, Double>, Double>> = mutableListOf()

                        //Loop over the sorted list 5 times to return 5 closest suburbs
                        var count : Int = 0
                        for (i in sortedList){
                            count += 1
                            if (count <= 5){
                                top5List.add(i)
                            }
                            else {
                                break
                            }
                        }

                        //Assign the 5 closest suburb data to a list
                        val sorted5List = top5List.sortedBy { it.second }
                        val list = ArrayList<nearMeList>()
                        for (i in sorted5List){
                            val item = nearMeList(i.first.first, i.first.second, i.second)
                            list += item
                        }
                        returnText.text = ""

                        //Display the 5 suburbs and data in the recycler view
                        val data = findViewById<RecyclerView>(R.id.nearList)
                        data.adapter = nearAdapter(list)
                        data.layoutManager = LinearLayoutManager(this@NearActivity)
                        data.setHasFixedSize(true)
                        break
                    }

                    //Display error message
                    else{
                        returnText.text = "${userSuburb} is not a valid Victorian suburb"
                    }
                }
            }
        }
    }
}