package com.example.carpool2

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class VehicleViewModel : ViewModel() {
    private val vehicleRepository: VehicleRepository
    val allVehicles: LiveData<List<Vehicle>>

    init {
        val vehicleDao = MainApplication.database.vehicleDao()
        vehicleRepository = VehicleRepository(vehicleDao)
        allVehicles = vehicleRepository.allVehicles
    }


}