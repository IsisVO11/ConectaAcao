package com.example.conectaao.model

import androidx.annotation.DrawableRes
import com.google.android.gms.maps.model.LatLng
import java.util.Date

/**
 * Modelo para posts na comunidade
 */
data class Post(
    val id: String = "",
    val authorId: String = "",
    val authorName: String = "",
    val authorImage: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val likes: Int = 0,
    val comments: List<Comment> = emptyList(),
    val timeAgo: String = "",
    @DrawableRes val imageResId: Int? = null
)

/**
 * Modelo para comentários nos posts
 */
data class Comment(
    val id: String,
    val authorName: String,
    val content: String,
    val timestamp: Long
)

/**
 * Enum para disponibilidade dos voluntários
 */
enum class Availability {
    UNDEFINED,
    MORNINGS,
    AFTERNOONS,
    EVENINGS,
    WEEKENDS,
    WEEKDAYS,
    FULL_TIME
}

/**
 * Modelo para locais no mapa
 */
data class Place(
    val id: String,
    val name: String,
    val address: String,
    val description: String,
    val type: MarkerType,
    val location: LatLng,
    val phone: String = "",
    val website: String = "",
    val isAccessible: Boolean = false,
    val accessibilityFeatures: List<String> = emptyList()
)

/**
 * Modelo para avaliações de voluntários
 */
data class Review(
    val id: String,
    val userId: String,
    val volunteerId: String,
    val rating: Float,
    val comment: String,
    val timestamp: Long
)

/**
 * Modelo para solicitações de ajuda
 */
data class HelpRequest(
    val id: String,
    val userId: String,
    val title: String,
    val description: String,
    val location: LatLng,
    val address: String,
    val timestamp: Long,
    val status: RequestStatus,
    val volunteerId: String? = null,
    val urgency: UrgencyLevel = UrgencyLevel.NORMAL
)

/**
 * Enum para status de solicitações
 */
enum class RequestStatus {
    PENDING,
    ACCEPTED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

/**
 * Enum para nível de urgência de solicitações
 */
enum class UrgencyLevel {
    LOW,
    NORMAL,
    HIGH,
    EMERGENCY
}

/**
 * Modelo para eventos da comunidade
 */
data class Event(
    val id: String,
    val title: String,
    val description: String,
    val location: LatLng,
    val address: String,
    val startTime: Long,
    val endTime: Long,
    val organizer: String,
    val isAccessible: Boolean = false,
    val accessibilityFeatures: List<String> = emptyList(),
    @DrawableRes val imageResId: Int? = null
) 