package com.example.carpool2.entity



data class Message(
    var message: String = "",    // The actual message content
    var sender: String = "",     // The sender's email or ID
    var timestamp: Long = 0      // Timestamp for ordering the messages
)

