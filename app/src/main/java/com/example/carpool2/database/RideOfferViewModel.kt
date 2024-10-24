package com.example.carpool2.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.carpool2.MainApplication
import com.example.carpool2.entity.RideOffer

class RideOfferViewModel : ViewModel() {
    private val rideOfferRepository: RideOfferRepository
    val allRideOffers: LiveData<List<RideOffer>>

    init {
        val rideOfferDao = MainApplication.database.rideOfferDao()
        rideOfferRepository = RideOfferRepository(rideOfferDao)
        allRideOffers = rideOfferRepository.allRideOffers
    }
}
