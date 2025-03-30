package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["remetenteId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["destinatarioId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Assistance::class,
            parentColumns = ["id"],
            childColumns = ["atendimentoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("remetenteId"), Index("destinatarioId"), Index("atendimentoId")]
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val remetenteId: Long,
    val destinatarioId: Long,
    val conteudo: String,
    val dataHora: Date = Date(),
    val lida: Boolean = false,
    val atendimentoId: Long?
) 