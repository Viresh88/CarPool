package com.example.carpool2.database

import com.example.carpool2.entity.Vehicle

class VehicleRepository(private val vehicleDao: VehicleDao) {
    val allVehicles = vehicleDao.getAllVehicles()

    suspend fun insert(vehicle: Vehicle){
        vehicleDao.insertVehicle(vehicle)
    }
}