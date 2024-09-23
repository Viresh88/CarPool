package com.example.carpool2

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.carpool2.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomePageActivity : AppCompatActivity() {
    private lateinit var homePageBinding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(homePageBinding.root)

        val findPoolButton: Button = findViewById(R.id.findPoolButton)
        val offerPoolButton : Button = findViewById(R.id.offerPoolButton)
        findPoolButton.setOnClickListener {
            findPoolButton.setBackgroundColor(Color.parseColor("#FF9800")) // Orange color
            offerPoolButton.setBackgroundColor(Color.parseColor("#E0E0E0"))

            val pickupLocation = findViewById<EditText>(R.id.pickupLocation).text.toString()
            val dropLocation = findViewById<EditText>(R.id.dropLocation).text.toString()

            if (pickupLocation.isNotEmpty() && dropLocation.isNotEmpty()) {
                val ride = Ride(pickupLocation, dropLocation)
                val rideStorage = RideDatabaseHelper(this)
                rideStorage.saveRide(ride)

                Toast.makeText(this, "Ride saved to My Rides!", Toast.LENGTH_SHORT).show()

                // Optionally, navigate to "My Rides" section
            } else {
                Toast.makeText(this, "Please enter both pickup and drop locations", Toast.LENGTH_SHORT).show()
            }
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

                R.id.requestRide -> {
                    true
                }

//                R.id.chat -> {
//                    true
//                }
//
//                R.id.profile -> {
//                    true
//                }

                else -> false
            }

        }
    }
}