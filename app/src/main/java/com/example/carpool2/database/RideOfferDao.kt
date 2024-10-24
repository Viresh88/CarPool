package com.example.carpool2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carpool2.entity.RideOffer

@Dao
interface RideOfferDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRideOffer(rideOffer: RideOffer)



    @Query("SELECT * FROM ride_offers")
    fun getAllRideOffers(): LiveData<List<RideOffer>>
}