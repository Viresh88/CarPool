package com.example.carpool2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.carpool2.adapters.ViewPagerAdapter
import com.example.carpool2.databinding.ActivityHomePageBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomePageActivity : AppCompatActivity() {
   private lateinit var homePageBinding: ActivityHomePageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(homePageBinding.root)

        homePageBinding.viewPager.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(homePageBinding.tabLayout, homePageBinding.viewPager) {tab, position ->
                when(position) {
                    0 -> tab.text = "Driver"
                    1 -> tab.text = "Passenger"
                }
        }.attach()
    }
}