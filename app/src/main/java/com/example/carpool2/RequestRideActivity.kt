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

class RequestRideActivity : AppCompatActivity() {
    private lateinit var vehicleAdapter: VehicleAdapter
    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_ride)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        vehicleViewModel.allVehicles.observe(this, Observer { vehicles ->
            vehicleAdapter = VehicleAdapter(vehicles)
            recyclerView.adapter = vehicleAdapter
        })

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.primary_dark)

        // Set the correct menu item as selected
        bottomNavigationView.selectedItemId = R.id.requestRide

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
                    // If already on the RequestRideActivity, no need to start the activity again
                    true
                }

                R.id.profile->{
                    val intent = Intent(this, ProfileActivity::class.java)
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
