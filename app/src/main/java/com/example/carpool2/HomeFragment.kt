package com.example.carpool2

import android.R
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.carpool2.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)
            activity?.finish() // Close HomePageFragment so the user cannot go back
        }

        // Set current date and time
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault())
        binding.dateTimeValue.setText(dateFormat.format(currentDate))

        // Set onClickListener for DatePickerDialog
        binding.dateTimeValue.setOnClickListener {
            showDatePickerAndTimePicker(binding.dateTimeValue)
        }

        MainApplication.database = MainApplication.database!!

        // Layout references
        val offerPoolButton: Button = binding.offerPoolButton
        val findPoolButton: Button = binding.findPoolButton
        val mainLayout: ConstraintLayout = binding.main
        val main2Layout: ConstraintLayout = binding.main2
        val btnNext: Button = binding.btnNext

        // Spinners
        val spinnerYear: Spinner = binding.spinnerYear
        val spinnerSeats: Spinner = binding.spinnerSeats

        // Set up button click for offering pool
        binding.btnNext.setOnClickListener {
            val pickupLocation = binding.pickupLocation1.text.toString()
            val dropLocation = binding.dropLocation1.text.toString()
            val vehicleModel = binding.etModel.text.toString()
            val vehicleYear = spinnerYear.selectedItem.toString().trim()
            val vehicleSeats = spinnerSeats.selectedItem.toString().trim()
            val number = binding.etVehicleNumber.text.toString()

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
                val vehicle = Vehicle(0, pickupLocation, dropLocation, vehicleModel, vehicleYear.toInt(), number, vehicleSeats.toInt())
                insert(vehicle)

                val intent = Intent(activity, RequestRideActivity::class.java)
                startActivity(intent)
            }
        }

        // Set up spinners
        val years = getVehicleYears()
        val yearAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerYear.adapter = yearAdapter

        val seats = listOf("2", "4", "5", "7", "8")
        val seatsAdapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, seats)
        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerSeats.adapter = seatsAdapter


    }

    private fun showDatePickerAndTimePicker(dateTimeEditText: EditText) {
        val calendar = Calendar.getInstance()

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                showTimePicker(calendar, dateTimeEditText)
            },
            year, month, day
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun showTimePicker(calendar: Calendar, dateTimeEditText: EditText) {
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
                calendar.set(Calendar.MINUTE, selectedMinute)

                val dateFormat = SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault())
                dateTimeEditText.setText(dateFormat.format(calendar.time))
            },
            hour, minute, false
        )
        timePickerDialog.show()
    }

    private fun getVehicleYears(): List<String> {
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val years = mutableListOf<String>()
        for (i in 1990..currentYear) {
            years.add(i.toString())
        }
        return years.reversed()
    }

    private fun saveRideOffer(rideOffer: RideOffer) {
        CoroutineScope(Dispatchers.IO).launch {
            MainApplication.database?.rideOfferDao()?.insertRideOffer(rideOffer)
        }
    }

    private fun insert(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            MainApplication.database?.vehicleDao()?.insertVehicle(vehicle)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
