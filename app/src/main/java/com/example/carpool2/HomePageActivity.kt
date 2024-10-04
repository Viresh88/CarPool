package com.example.carpool2

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.Color
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.core.view.get
import com.example.carpool2.MainApplication.Companion.database
import com.example.carpool2.databinding.ActivityHomePageBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class HomePageActivity : AppCompatActivity() {
    private lateinit var homePageBinding: ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        homePageBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(homePageBinding.root)
        val dateTimeEditText = findViewById<EditText>(R.id.date_time_value)
        // Set current date and time
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault())
        dateTimeEditText.setText(dateFormat.format(currentDate))

        // Set onClickListener for DatePickerDialog
        dateTimeEditText.setOnClickListener {
            showDatePickerAndTimePicker(dateTimeEditText)
        }

        database = MainApplication.database!!

        val offerPoolButton: Button = findViewById(R.id.offerPoolButton)
        val findPoolButton: Button = findViewById(R.id.findPoolButton)
        val mainLayout = findViewById<ConstraintLayout>(R.id.main)
        val main2Layout = findViewById<ConstraintLayout>(R.id.main2)
        val btnNext = findViewById<Button>(R.id.btnNext)

        //offer pool layout
        val pickupLocation1 :EditText = findViewById(R.id.pickupLocation1)
        val dropLocation1 : EditText = findViewById(R.id.dropLocation1)
        val spinnerYear: Spinner = findViewById(R.id.spinner_year)
        val spinnerSeats: Spinner = findViewById(R.id.spinner_seats)
        val vehicleModel: EditText = findViewById(R.id.et_model)
        val vehicleNumber: EditText = findViewById(R.id.et_vehicle_number)
        val buttonNext: Button = findViewById(R.id.btn_next)

        buttonNext.setOnClickListener {
            val pickupLocation = pickupLocation1.text.toString()
            val dropLocation = dropLocation1.text.toString()
             val vehicleModel = vehicleModel.text.toString()
             val vehicleYear = spinnerYear.selectedItem.toString().trim()
            val vehicleSeats = spinnerSeats.selectedItem.toString().trim()
            val number = vehicleNumber.text.toString()

            // Validate if all fields are filled
            if (pickupLocation.isEmpty()) {
                showToast("Please enter Pickup Location")
            } else if (dropLocation.isEmpty()) {
                showToast("Please enter Drop Location")
            } else if (vehicleModel.isEmpty()) {
                showToast("Please enter Vehicle Model")
            } else if (vehicleYear == "Select Year") {
                showToast("Please select Vehicle Year")
            } else if (vehicleSeats == "Select Seats") {
                showToast("Please select Number of Seats")
            } else if (number.isEmpty()) {
                showToast("Please enter Vehicle Number")
            } else {
                val vehicle = Vehicle(0,pickupLocation, dropLocation, vehicleModel, vehicleYear.toInt(), number, vehicleSeats.toInt())

                insert(vehicle)

                val intent = Intent(this, RequestRideActivity::class.java)
                startActivity(intent)
            }
        }

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


        // Initially, set the "Find Pool" button as active
        findPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_active))
        offerPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_inactive))
        var selectedSeats : Int = 0
        val seatbtn1 = findViewById<Button>(R.id.seat_1)
        val seatbtn2 = findViewById<Button>(R.id.seat_2)
        val seatbtn3 = findViewById<Button>(R.id.seat_3)

        // Function to highlight the selected seat button and store the seat number
        fun highlightSelectedSeat(selectedButton: Button, seatNumber: Int) {
            // Reset all button backgrounds
            seatbtn1.setBackgroundResource(R.drawable.seat_unselected_background)
            seatbtn2.setBackgroundResource(R.drawable.seat_unselected_background)
            seatbtn3.setBackgroundResource(R.drawable.seat_unselected_background)

            // Reset text colors to unselected state
            seatbtn1.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            seatbtn2.setTextColor(ContextCompat.getColor(this, android.R.color.black))
            seatbtn3.setTextColor(ContextCompat.getColor(this, android.R.color.black))

            // Highlight the selected button
            // Highlight the selected button
            selectedButton.setBackgroundResource(R.drawable.seat_selected_background)
            selectedButton.setTextColor(ContextCompat.getColor(this, android.R.color.white))

            // Store the selected number of seats
            selectedSeats = seatNumber
        }

        // Set click listeners for seat buttons
        seatbtn1.setOnClickListener {
            highlightSelectedSeat(seatbtn1, 1)
        }

        seatbtn2.setOnClickListener {
            highlightSelectedSeat(seatbtn2, 2)
        }

        seatbtn3.setOnClickListener {
            highlightSelectedSeat(seatbtn3, 3)
        }


        btnNext.setOnClickListener {
            mainLayout.visibility = View.VISIBLE
            main2Layout.visibility = View.GONE
            findPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_active))
            offerPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_inactive))
            val pickupLocation: String = findViewById<EditText>(R.id.pickupLocation).text.toString()
            val dropLocation: String = findViewById<EditText>(R.id.dropLocation).text.toString()
            val dateTime: String = findViewById<EditText>(R.id.date_time_value).text.toString()

            if (pickupLocation.isEmpty()) {
                showToast("Please enter the pickup location")
            }
            else if (dropLocation.isEmpty()) {
               showToast("Please enter the drop location")
            }
            else if (dateTime.isEmpty()) {
                showToast("Please select a date and time")
            }
            else if (selectedSeats == 0) {
               showToast("Please select the number of seats")
            }

//            if (selectedSeats == 0) {
//                Toast.makeText(this, "Please select the number of seats", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
           else {
                val rideOffer = RideOffer(0, pickupLocation, dropLocation, dateTime, selectedSeats)
                saveRideOffer(rideOffer)


                val intent = Intent(this, MyRidesActivity::class.java)
                startActivity(intent)
            }
        }




        offerPoolButton.setOnClickListener {
            main2Layout.visibility = View.VISIBLE
            mainLayout.visibility = View.GONE
            offerPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_active))
            findPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_inactive))
        }

        findPoolButton.setOnClickListener {
            main2Layout.visibility = View.GONE
            mainLayout.visibility = View.VISIBLE
            offerPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_inactive))
            findPoolButton.setBackgroundColor(ContextCompat.getColor(this, R.color.button_active))
        }




        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.itemIconTintList = ContextCompat.getColorStateList(this, R.color.primary_dark)
        bottomNavigationView.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> {
                    val intent = Intent(this, HomePageActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.myRides -> {
                   val intent = Intent(this, MyRidesActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.chat -> {
                    val intent = Intent(this, UserListActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.requestRide-> {
                    val intent = Intent(this, RequestRideActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.profile -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                    finish()
                    true
                }

                else -> false
            }

        }
    }



    private fun showDatePickerAndTimePicker(dateTimeEditText: EditText) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // First, show DatePickerDialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                // Update calendar with selected date
                calendar.set(selectedYear, selectedMonth, selectedDay)

                // Now, show TimePickerDialog
                showTimePicker(calendar, dateTimeEditText)
            },
            year, month, day
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis // Disable past dates
        datePickerDialog.show()
    }

    private fun showTimePicker(calendar: Calendar, dateTimeEditText: EditText) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        // Show TimePickerDialog
        val timePickerDialog = TimePickerDialog(
            this,
            { _, selectedHour, selectedMinute ->
                // Update calendar with selected time
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                // Format and set the selected date and time into EditText
                val dateFormat = SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault())
                dateTimeEditText.setText(dateFormat.format(calendar.time))
            },
            hour, minute, false // Set false for 12-hour format, true for 24-hour format
        )
        timePickerDialog.show()
    }
    private fun getVehicleYears(): List<String> {
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val years = mutableListOf<String>()
        for (i in 1990..currentYear) {
            years.add(i.toString())
        }
        return years.reversed() // Reverse to show recent years first
    }
    private fun saveRideOffer(rideOffer: RideOffer) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.rideOfferDao()?.insertRideOffer(rideOffer)
        }
    }
    fun insert(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.vehicleDao()?.insertVehicle(vehicle)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}