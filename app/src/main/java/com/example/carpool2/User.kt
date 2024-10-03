package com.example.carpool2



import java.io.Serializable

data class User(
    var name: String = "",
    var email: String = "",
    var status: String = "Online"
) : Serializable
