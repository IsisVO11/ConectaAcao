package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "user_profiles",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId", unique = true)]
)
data class UserProfile(
    @PrimaryKey val userId: Long,
    val endereco: String?,
    val cidade: String?,
    val estado: String?,
    val cep: String?,
    val latitude: Double?,
    val longitude: Double?,
    val biografia: String?,
    val necessidadesEspecificas: String?, // Para PCDs e idosos
    val habilidades: String?, // Para voluntários
    val disponibilidade: String? // Para voluntários
) 