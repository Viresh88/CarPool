package com.example.carpool2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RideAdapter(private val rideList: List<RideOffer>) :
    RecyclerView.Adapter<RideAdapter.RideViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RideViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ride_item, parent, false)
        return RideViewHolder(view)
    }

    override fun onBindViewHolder(holder: RideViewHolder, position: Int) {
        val ride = rideList[position]
        holder.pickupTextView.text = "Pickup Location: ${ride.pickupLocation}"
        holder.dropTextView.text = "Drop Location: ${ride.dropLocation}"
        holder.dateTimeTxtView.text = "Date and Time : ${ride.dateTime}"
        holder.seatsTxtView.text = "Seats : ${ride.numberOfSeats}"
    }

    override fun getItemCount(): Int {
        return rideList.size
    }

    class RideViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pickupTextView: TextView = itemView.findViewById(R.id.tvPickupLocation)
        val dropTextView: TextView = itemView.findViewById(R.id.tvDropLocation)
        val dateTimeTxtView : TextView = itemView.findViewById(R.id.tvDateTime)
        val seatsTxtView : TextView = itemView.findViewById(R.id.tvSeats)
    }
}


