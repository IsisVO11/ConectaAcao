package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "requests",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["solicitanteId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("solicitanteId")]
)
data class Request(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val solicitanteId: Long,
    val tipoServico: String,
    val descricao: String,
    val dataHora: Date = Date(),
    val status: String, // "PENDENTE", "ACEITA", "CONCLUIDA", "CANCELADA"
    val latitude: Double?,
    val longitude: Double?,
    val urgencia: String // "BAIXA", "MEDIA", "ALTA"
) 