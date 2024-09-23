package com.example.carpool2


import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyRidesActivity : AppCompatActivity() {
    private lateinit var rideAdapter: RideAdapter
    private lateinit var rideStorage: RideDatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_rides)

        rideStorage = RideDatabaseHelper(this)

        // Get all rides from the database
        val allRides = rideStorage.getAllRides()

        // Setup RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        rideAdapter = RideAdapter(allRides)
        recyclerView.adapter = rideAdapter

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.myRides -> {
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


