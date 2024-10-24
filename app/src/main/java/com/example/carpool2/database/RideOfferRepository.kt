package com.example.carpool2.database

import androidx.lifecycle.LiveData
import com.example.carpool2.entity.RideOffer

class RideOfferRepository(private val rideOfferDao: RideOfferDao) {
    val allRideOffers: LiveData<List<RideOffer>> = rideOfferDao.getAllRideOffers()
}