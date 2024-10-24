package com.example.carpool2.fragments

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.icu.text.SimpleDateFormat
import android.icu.util.Calendar
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.carpool2.MainActivity
import com.example.carpool2.MainApplication
import com.example.carpool2.MainApplication.Companion.database
import com.example.carpool2.R
import com.example.carpool2.entity.RideOffer
import com.example.carpool2.entity.Vehicle
import com.example.carpool2.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Locale

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var auth: FirebaseAuth
    private var selectedSeats: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        homeBinding = FragmentHomeBinding.inflate(inflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()

        homeBinding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(requireActivity(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish() // Close HomeFragment so the user cannot go back
        }

        // Set current date and time
        val currentDate = Calendar.getInstance().time
        val dateFormat = SimpleDateFormat("EEE, MMM d, h:mm a", Locale.getDefault())
        homeBinding.dateTimeValue.setText(dateFormat.format(currentDate))

        // Set onClickListener for DatePickerDialog
        homeBinding.dateTimeValue.setOnClickListener {
            showDatePickerAndTimePicker(homeBinding.dateTimeValue)
        }

        database = MainApplication.database!!

        // Set click listeners for layout visibility
        homeBinding.findPoolButton.setOnClickListener {
            switchToFindPoolLayout()
        }

        homeBinding.offerPoolButton.setOnClickListener {
            switchToOfferPoolLayout()
        }

        // Set spinner adapters
        setupSpinners()

        // Seat selection buttons
        setupSeatButtons()

        // Next button click listener
        homeBinding.btnNext.setOnClickListener {
            validateAndSaveRide()
        }

        homeBinding.btnnextOffer.setOnClickListener {
            validateAndSaveVehicle()
        }
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

    private fun setupSpinners() {
        // Spinner for Vehicle Year
        val years = getVehicleYears()
        val yearAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, years)
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        homeBinding.spinnerYear.adapter = yearAdapter

        // Spinner for Seats
        val seats = listOf("2", "4", "5", "7", "8")
        val seatsAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, seats)
        seatsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        homeBinding.spinnerSeats.adapter = seatsAdapter
    }

    private fun getVehicleYears(): List<String> {
        val currentYear = java.util.Calendar.getInstance().get(java.util.Calendar.YEAR)
        val years = mutableListOf<String>()
        for (i in 1990..currentYear) {
            years.add(i.toString())
        }
        return years.reversed()
    }

    private fun setupSeatButtons() {
        homeBinding.seat1.setOnClickListener {
            highlightSelectedSeat(homeBinding.seat1, 1)
        }
        homeBinding.seat2.setOnClickListener {
            highlightSelectedSeat(homeBinding.seat2, 2)
        }
        homeBinding.seat3.setOnClickListener {
            highlightSelectedSeat(homeBinding.seat3, 3)
        }
    }

    private fun highlightSelectedSeat(selectedButton: Button, seatNumber: Int) {
        homeBinding.seat1.setBackgroundResource(R.drawable.seat_unselected_background)
        homeBinding.seat2.setBackgroundResource(R.drawable.seat_unselected_background)
        homeBinding.seat3.setBackgroundResource(R.drawable.seat_unselected_background)

        homeBinding.seat1.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        homeBinding.seat2.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
        homeBinding.seat3.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))

        selectedButton.setBackgroundResource(R.drawable.seat_selected_background)
        selectedButton.setTextColor(ContextCompat.getColor(requireContext(), android.R.color.white))

        selectedSeats = seatNumber
    }

    private fun switchToFindPoolLayout() {
        homeBinding.main.visibility = View.VISIBLE
        homeBinding.main2.visibility = View.GONE
        homeBinding.findPoolButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.button_active
        ))
        homeBinding.offerPoolButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.button_inactive
        ))
    }

    private fun switchToOfferPoolLayout() {
        homeBinding.main2.visibility = View.VISIBLE
        homeBinding.main.visibility = View.GONE
        homeBinding.offerPoolButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.button_active
        ))
        homeBinding.findPoolButton.setBackgroundColor(ContextCompat.getColor(requireContext(),
            R.color.button_inactive
        ))
    }

    private fun validateAndSaveRide() {
        val pickupLocation = homeBinding.pickupLocation.text.toString()
        val dropLocation = homeBinding.dropLocation.text.toString()
        val dateTime = homeBinding.dateTimeValue.text.toString()

        when {
            pickupLocation.isEmpty() -> showToast("Please enter the pickup location")
            dropLocation.isEmpty() -> showToast("Please enter the drop location")
            dateTime.isEmpty() -> showToast("Please select a date and time")
            selectedSeats == 0 -> showToast("Please select the number of seats")
            else -> {
                val rideOffer = RideOffer(0, pickupLocation, dropLocation, dateTime, selectedSeats)
                saveRideOffer(rideOffer)
//                val intent = Intent(requireActivity(), MyRidesActivity::class.java)
//                startActivity(intent)

                val myRidesFragment = MyRidesFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, myRidesFragment) // Replace R.id.fragment_container with your actual container ID
                transaction.addToBackStack(null) // Optional, if you want to add this transaction to the backstack
                transaction.commit()
            }
        }
    }

    private fun validateAndSaveVehicle() {
        val pickupLocation = homeBinding.pickupLocation1.text.toString()
        val dropLocation = homeBinding.dropLocation1.text.toString()
        val vehicleModel = homeBinding.etModel.text.toString()
        val vehicleYear = homeBinding.spinnerYear.selectedItem.toString().trim()
        val vehicleSeats = homeBinding.spinnerSeats.selectedItem.toString().trim()
        val number = homeBinding.etVehicleNumber.text.toString()

        when {
            pickupLocation.isEmpty() -> showToast("Please enter Pickup Location")
            dropLocation.isEmpty() -> showToast("Please enter Drop Location")
            vehicleModel.isEmpty() -> showToast("Please enter Vehicle Model")
            vehicleYear == "Select Year" -> showToast("Please select Vehicle Year")
            vehicleSeats == "Select Seats" -> showToast("Please select Number of Seats")
            number.isEmpty() -> showToast("Please enter Vehicle Number")
            else -> {
                val vehicle = Vehicle(0, pickupLocation, dropLocation, vehicleModel, vehicleYear.toInt(), number, vehicleSeats.toInt())
                insert(vehicle)
//                val intent = Intent(requireActivity(), RequestRideActivity::class.java)
//                startActivity(intent)

                val fragment = RequestRideFragment()
                val transaction = requireActivity().supportFragmentManager.beginTransaction()
                transaction.replace(R.id.fragment_container, fragment) // Replace R.id.fragment_container with your actual container ID
                transaction.addToBackStack(null) // Optional, if you want to add this transaction to the backstack
                transaction.commit()
            }
        }
    }

    private fun saveRideOffer(rideOffer: RideOffer) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.rideOfferDao()?.insertRideOffer(rideOffer)
        }
    }

    private fun insert(vehicle: Vehicle) {
        CoroutineScope(Dispatchers.IO).launch {
            database?.vehicleDao()?.insertVehicle(vehicle)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
