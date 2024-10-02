package com.example.carpool2

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class OfferPool : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_offer_pool)
        sharedPreferences = getSharedPreferences("VehicleInfo", Context.MODE_PRIVATE)
        val spinnerYear: Spinner = findViewById(R.id.spinner_year)
        val spinnerSeats: Spinner = findViewById(R.id.spinner_seats)
        val editTextModel: EditText = findViewById(R.id.et_model)
        val editTextNumber: EditText = findViewById(R.id.et_vehicle_number)
        val buttonNext: Button = findViewById(R.id.btn_next)
        // Spinner for Vehicle Year

        val years = getVehicleYears() // Function to generate a list of years
        val yearAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter

        // Spinner for Seats
        val seats = listOf("2", "4", "5", "7", "8")
        val seatsAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, seats)
        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeats.adapter = seatsAdapter

        // Load previously saved data (if exists)
        loadSavedData( editTextModel, editTextNumber, spinnerYear, spinnerSeats)

        // Set click listener on the "Next" button
        buttonNext.setOnClickListener {
            // Get user input
            val vehicleModel = editTextModel.text.toString()
            val vehicleYear = spinnerYear.selectedItem.toString()
            val vehicleNumber = editTextNumber.text.toString()
            val vehicleSeats = spinnerSeats.selectedItem.toString()

            // Save data in SharedPreferences
            saveData(vehicleModel, vehicleYear, vehicleNumber, vehicleSeats)

            // Create an Intent to navigate to the RequestRideActivity
            val intent = Intent(this, RequestRideActivity::class.java).apply {
                putExtra("vehicle_model", vehicleModel)
                putExtra("vehicle_year", vehicleYear)
                putExtra("vehicle_number", vehicleNumber)
                putExtra("vehicle_seats", vehicleSeats)
            }

            // Start the RequestRideActivity
            startActivity(intent)
        }
    }

    // Save data to SharedPreferences
    private fun saveData( model: String, year: String, number: String, seats: String) {
        val editor = sharedPreferences.edit()
        editor.putString("vehicle_model", model)
        editor.putString("vehicle_year", year)
        editor.putString("vehicle_number", number)
        editor.putString("vehicle_seats", seats)
        editor.apply()
    }

    // Load data from SharedPreferences
    private fun loadSavedData(
        editTextModel: EditText,
        editTextNumber: EditText,
        spinnerYear: Spinner,
        spinnerSeats: Spinner
    ) {
        val savedModel = sharedPreferences.getString("vehicle_model", "")
        val savedYear = sharedPreferences.getString("vehicle_year", "")
        val savedNumber = sharedPreferences.getString("vehicle_number", "")
        val savedSeats = sharedPreferences.getString("vehicle_seats", "")

        // Set saved data to EditTexts
        editTextModel.setText(savedModel)
        editTextNumber.setText(savedNumber)

        // Set saved year in Spinner
        val yearIndex = getVehicleYears().indexOf(savedYear)
        if (yearIndex >= 0) {
            spinnerYear.setSelection(yearIndex)
        }

        // Set saved seats in Spinner
        val seatsIndex = listOf("2", "4", "5", "7", "8").indexOf(savedSeats)
        if (seatsIndex >= 0) {
            spinnerSeats.setSelection(seatsIndex)
        }
    }

    private fun getVehicleYears(): List<String> {
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val years = mutableListOf<String>()
        for (i in 1990..currentYear) {
            years.add(i.toString())
        }
        return years.reversed() // Reverse to show recent years first
    }
}