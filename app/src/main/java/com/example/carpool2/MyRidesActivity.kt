package com.example.carpool2

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class MyRidesActivity : AppCompatActivity() {
    private lateinit var rideAdapter: RideAdapter
    private val viewModel: RideOfferViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_rides)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        viewModel.allRideOffers.observe(this, Observer { rideOffers ->
            recyclerView.layoutManager = LinearLayoutManager(this)
            rideAdapter = RideAdapter(rideOffers)
            recyclerView.adapter = rideAdapter
        })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.primary_dark)
        // Set the correct menu item as selected
        bottomNavigationView.selectedItemId = R.id.myRides

        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomeFragment::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.chat -> {
                    val intent = Intent(this, UserListActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.myRides -> {
                    val intent = Intent(this, MyRidesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.requestRide -> {
                    val intent = Intent(this, RequestRideActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        // Navigate to HomeActivity when back button is pressed
        val intent = Intent(this, HomeFragment::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
        finish() // Finish the current activity
    }
}

