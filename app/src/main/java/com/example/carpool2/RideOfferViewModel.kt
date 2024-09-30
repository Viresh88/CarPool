package com.example.carpool2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class RideOfferViewModel : ViewModel() {
    private val rideOfferRepository: RideOfferRepository
    val allRideOffers: LiveData<List<RideOffer>>

    init {
        val rideOfferDao = MainApplication.database.rideOfferDao()
        rideOfferRepository = RideOfferRepository(rideOfferDao)
        allRideOffers = rideOfferRepository.allRideOffers
    }
}
