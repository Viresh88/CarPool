package com.example.carpool2.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.carpool2.entity.Vehicle

@Dao
interface VehicleDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVehicle(vehicle: Vehicle)

    @Query("SELECT * FROM vehicles")
    fun getAllVehicles(): LiveData<List<Vehicle>>
}
