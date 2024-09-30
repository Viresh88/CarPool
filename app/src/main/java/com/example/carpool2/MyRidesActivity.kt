package com.example.carpool2


import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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


        // Setup RecyclerView



        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, MyRidesActivity::class.java)
                    startActivity(intent)
                    true
                }

                R.id.myRides -> {
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


