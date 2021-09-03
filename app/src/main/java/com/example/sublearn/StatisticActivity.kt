package com.example.sublearn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import java.io.InputStream


class StatisticActivity : AppCompatActivity() {
    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)

        //Identify button to navigate from this activity to welcome fragment on button click
        findViewById<ImageButton>(R.id.buttonStatistics).setOnClickListener {
            val intent =
                Intent(this@StatisticActivity, MainActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }

        //Identify button to display suburb statistics on button click
        findViewById<Button>(R.id.buttonStatistics2).setOnClickListener {

            //Identify user inputs and edit view and convert to string
            val suburb = findViewById<EditText>(R.id.inputStatistics)
            val userSuburb = suburb.text.toString().toLowerCase()
            val returnText = findViewById<TextView>(R.id.statInfo)
            var check = false

            //Read through .csv file and iterate through each line
            //If iterated row index 0 is equal to users input display statistics, else return an error
            val inputStream: InputStream = assets.open("complete_data.csv")
            csvReader().open(inputStream) {
                for (row: List<String> in readAllAsSequence()){
                    if (row[0].toLowerCase() == userSuburb){
                        check = true
                        returnText.text = "Suburb name: ${row[0]} \nReported crashes: ${row[3]} \n" +
                                "Males involved: ${row[4]} \n" +
                                "Females involved: ${row[5]} \n" +
                                "Cyclists involved: ${row[6]} \n" +
                                "Pedestrians involved: ${row[7]} \n" +
                                "Alcohol related: ${row[8]} \n" +
                                "Fatal injuries: ${row[9]}"
                        break
                    }
                }
                //Display error message
                if (!check) {
                    suburb.error = "Please enter a Victorian suburb"
                    suburb.requestFocus()
                }
            }
        }
    }
}