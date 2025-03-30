package com.example.conectaao.model

enum class MarkerType {
    HEALTH_CENTER,
    POLICE_STATION,
    FIRE_STATION,
    ACCESSIBLE_LOCATION,
    VOLUNTEER,
    HOSPITAL,
    POLICE,
    USER_LOCATION
}

data class PlaceMarker(
    val id: String = "",
    val name: String = "",
    val description: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val type: MarkerType = MarkerType.ACCESSIBLE_LOCATION
) 