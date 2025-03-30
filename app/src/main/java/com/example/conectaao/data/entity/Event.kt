package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "events",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["organizadorId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("organizadorId")]
)
data class Event(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val descricao: String,
    val organizadorId: Long,
    val dataHoraInicio: Date,
    val dataHoraFim: Date?,
    val endereco: String,
    val latitude: Double?,
    val longitude: Double?,
    val capacidade: Int?,
    val recursosAcessibilidade: String? // JSON ou texto com recursos disponíveis
) 