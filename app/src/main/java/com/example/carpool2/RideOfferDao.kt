package com.example.carpool2

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface RideOfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRideOffer(rideOffer: RideOffer)



    @Query("SELECT * FROM ride_offers")
    fun getAllRideOffers(): LiveData<List<RideOffer>>
}