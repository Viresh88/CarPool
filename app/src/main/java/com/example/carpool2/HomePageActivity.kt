package com.example.carpool2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.example.carpool2.MainApplication.Companion.database
import com.example.carpool2.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HomePageActivity : AppCompatActivity() {
    private lateinit var homePageBinding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(homePageBinding.root)

        database = MainApplication.database!!

        val offerPoolButton: Button = findViewById(R.id.offerPoolButton)
        val findPoolButton: Button = findViewById(R.id.findPoolButton)
        findPoolButton.setOnClickListener {
            val pickupLocation: String = findViewById<EditText>(R.id.pickupLocation).text.toString()
            val dropLocation: String = findViewById<EditText>(R.id.dropLocation).text.toString()
            val dateTime: String = findViewById<TextView>(R.id.date_time_value).text.toString()
            val numberOfSeats: Int = 1

            val rideOffer = RideOffer(0, pickupLocation, dropLocation, dateTime, numberOfSeats)
            saveRideOffer(rideOffer)
        }




        offerPoolButton.setOnClickListener {
            offerPoolButton.setBackgroundColor(Color.parseColor("#FF9800")) // Orange color
            findPoolButton.setBackgroundColor(Color.parseColor("#E0E0E0"))
        }


        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    true
                }

                R.id.myRides -> {
                   val intent = Intent(this, MyRidesActivity::class.java)
                    startActivity(intent)
                    true
                }



                R.id.chat -> {
                    true
                }

                R.id.profile -> {
                    true
                }

                else -> false
            }

        }
    }
    private fun saveRideOffer(rideOffer: RideOffer) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.rideOfferDao()?.insertRideOffer(rideOffer)
        }
    }
}