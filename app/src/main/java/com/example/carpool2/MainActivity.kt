package com.example.carpool2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView: BottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set default fragment (e.g., HomeFragment)
        loadFragment(HomeFragment())

        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.home -> {
                    loadFragment(HomeFragment())
                     true
                }

//                R.id.nav_profile -> {
//                    loadFragment(ProfileFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }
//
//                R.id.nav_settings -> {
//                    loadFragment(SettingsFragment())
//                    return@setOnNavigationItemSelectedListener true
//                }

                else -> false
            }
        }
    }

    override fun onBackPressed() {
        // Check if there are fragments in the back stack
        if (supportFragmentManager.backStackEntryCount > 0) {
            // If there is, pop the back stack and go to the previous fragment
            supportFragmentManager.popBackStack()
        } else {
            // If no fragments in back stack, follow normal back press (e.g., exit the activity)
            super.onBackPressed()
        }
    }


   private fun loadFragment(fragment: Fragment) {
        // Load the selected fragment into the fragment container
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .addToBackStack(null) // This adds it to the back stack
            .commit()
    }
}