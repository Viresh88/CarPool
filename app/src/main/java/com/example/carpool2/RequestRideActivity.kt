package com.example.carpool2

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
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
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.myRides -> {
                    val intent = Intent(this, MyRidesActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }
}