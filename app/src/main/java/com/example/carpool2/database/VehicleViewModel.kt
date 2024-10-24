package com.example.carpool2.database

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.carpool2.MainApplication
import com.example.carpool2.entity.Vehicle

class VehicleViewModel : ViewModel() {
    private val vehicleRepository: VehicleRepository
    val allVehicles: LiveData<List<Vehicle>>

    init {
        val vehicleDao = MainApplication.database.vehicleDao()
        vehicleRepository = VehicleRepository(vehicleDao)
        allVehicles = vehicleRepository.allVehicles
    }


}