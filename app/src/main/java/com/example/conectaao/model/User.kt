package com.example.conectaao.model

data class User(
    val id: String = "",
    val name: String = "",
    val email: String = "",
    val isVolunteer: Boolean = false,
    val skills: List<String> = emptyList(),
    val rating: Float = 0f,
    val location: Location? = null
)

data class Location(
    val latitude: Double,
    val longitude: Double
) 