package com.example.conectaao.model

import com.example.conectaao.model.Availability

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val phone: String = "",
    val bio: String = "",
    val isVolunteer: Boolean = false,
    val skills: List<String> = emptyList(),
    val specialties: List<String> = emptyList(),
    val rating: Float = 0f,
    val location: Location? = null,
    val profileImage: String = "",
    val profileImageUrl: String = "",
    val distance: Double = 0.0,
    val specialty: String = "",
    val distanceDescription: String = "",
    val availability: Availability = Availability.UNDEFINED
)

data class Location(
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
) 