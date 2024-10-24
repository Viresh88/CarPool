package com.example.carpool2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.carpool2.R
import com.example.carpool2.entity.Vehicle

class VehicleAdapter(private val vehicleList: List<Vehicle>) :
    RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehicleViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item, parent, false)
        return VehicleViewHolder(view)
    }

    override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
        val vehicle = vehicleList[position]
        holder.pickupTextView.text = "Pickup Location: ${vehicle.pickupLocation}"
        holder.dropTextView.text = "Drop Location: ${vehicle.dropLocation}"
        holder.modelTextView.text = "Model: ${vehicle.model}"
        holder.yearTextView.text = "Year: ${vehicle.vehicleYear}"
        holder.numberTextView.text = "Number: ${vehicle.vehicleNumber}"
        holder.seatsTextView.text = "Seats: ${vehicle.seats}"
    }

    override fun getItemCount(): Int {
        return vehicleList.size
    }

    class VehicleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pickupTextView : TextView = itemView.findViewById(R.id.tvPickupLocation)
        val dropTextView : TextView = itemView.findViewById(R.id.tvDropLocation)
        val modelTextView: TextView = itemView.findViewById(R.id.tvVehicleModel)
        val yearTextView: TextView = itemView.findViewById(R.id.tvVehicleYear)
        val numberTextView: TextView = itemView.findViewById(R.id.tvVehicleNumber)
        val seatsTextView: TextView = itemView.findViewById(R.id.tvSeats)
    }
}
