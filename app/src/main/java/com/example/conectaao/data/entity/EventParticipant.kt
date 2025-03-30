package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import java.util.Date

@Entity(
    tableName = "event_participants",
    primaryKeys = ["eventoId", "usuarioId"],
    foreignKeys = [
        ForeignKey(
            entity = Event::class,
            parentColumns = ["id"],
            childColumns = ["eventoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("eventoId"), Index("usuarioId")]
)
data class EventParticipant(
    val eventoId: Long,
    val usuarioId: Long,
    val status: String, // "CONFIRMADO", "PENDENTE", "CANCELADO"
    val dataInscricao: Date = Date()
) 