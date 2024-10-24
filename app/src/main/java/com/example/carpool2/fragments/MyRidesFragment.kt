package com.example.carpool2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carpool2.adapters.RideAdapter
import com.example.carpool2.database.RideOfferViewModel
import com.example.carpool2.databinding.FragmentMyRidesBinding


class MyRidesFragment : Fragment() {
    private lateinit var rideAdapter: RideAdapter
    private val viewModel: RideOfferViewModel by viewModels()
    private lateinit var binding: FragmentMyRidesBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyRidesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
//        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe ride offers from the ViewModel
//        viewModel.allRideOffers.observe(viewLifecycleOwner, Observer { rideOffers ->
//            if (rideOffers.isNotEmpty()) {
//                rideAdapter = RideAdapter(rideOffers)
//                recyclerView.adapter = rideAdapter
//            } else {
//                // Show a message if there are no ride offers
//                Toast.makeText(requireContext(), "No rides found", Toast.LENGTH_SHORT).show()
//            }
//        })

        viewModel.allRideOffers.observe(viewLifecycleOwner, Observer { rideOffers ->
            println("Ride offers: $rideOffers")
            if (rideOffers.isNotEmpty()) {
                rideAdapter = RideAdapter(rideOffers)
                binding.recyclerView.adapter = rideAdapter
            } else {
                Toast.makeText(requireContext(), "No rides found", Toast.LENGTH_SHORT).show()
            }
        })

    }
}
