package com.example.carpool2

import androidx.lifecycle.LiveData

class RideOfferRepository(private val rideOfferDao: RideOfferDao) {
    val allRideOffers: LiveData<List<RideOffer>> = rideOfferDao.getAllRideOffers()
}