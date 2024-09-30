package com.example.carpool2

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "ride_offers")
data class RideOffer(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pickupLocation: String,
    val dropLocation: String,
    val dateTime: String,
    val numberOfSeats: Int
)
