package com.example.carpool2

class VehicleRepository(private val vehicleDao: VehicleDao) {
    val allVehicles = vehicleDao.getAllVehicles()

    suspend fun insert(vehicle: Vehicle){
        vehicleDao.insertVehicle(vehicle)
    }
}