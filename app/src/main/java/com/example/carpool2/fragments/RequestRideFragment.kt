package com.example.carpool2.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.carpool2.adapters.VehicleAdapter
import com.example.carpool2.database.VehicleViewModel
import com.example.carpool2.databinding.FragmentRequestRideBinding

class RequestRideFragment : Fragment() {

    private var _binding: FragmentRequestRideBinding? = null
    private val binding get() = _binding!!

    private lateinit var vehicleAdapter: VehicleAdapter
    private val vehicleViewModel: VehicleViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout using ViewBinding
        _binding = FragmentRequestRideBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Set up RecyclerView
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        vehicleViewModel.allVehicles.observe(viewLifecycleOwner, Observer { vehicles ->
            vehicleAdapter = VehicleAdapter(vehicles)
            binding.recyclerView.adapter = vehicleAdapter
        })
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Avoid memory leaks
    }
}
