package com.example.carpool2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.carpool2.fragments.DriverFragment
import com.example.carpool2.fragments.PassengerFragment

class ViewPagerAdapter(fa : FragmentActivity) : FragmentStateAdapter(fa) {
     override fun getItemCount(): Int {
         return 2
     }

     override fun createFragment(position: Int): Fragment {
         return when(position) {
             0 -> DriverFragment()
             1 -> PassengerFragment()
             else -> DriverFragment()
         }
     }
 }