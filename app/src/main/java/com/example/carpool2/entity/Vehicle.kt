package com.example.carpool2.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "vehicles")
data class Vehicle(
    @PrimaryKey(autoGenerate = true) val id:Int = 0,
    val pickupLocation: String,
    val dropLocation: String,
    val model: String,
    val vehicleYear : Int,
    val vehicleNumber : String,
    val seats : Int
)
