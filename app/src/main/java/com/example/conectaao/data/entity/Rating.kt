package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "ratings",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["avaliadorId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["avaliadoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Service::class,
            parentColumns = ["id"],
            childColumns = ["servicoId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("avaliadorId"), Index("avaliadoId"), Index("servicoId")]
)
data class Rating(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val avaliadorId: Long,
    val avaliadoId: Long,
    val servicoId: Long?,
    val nota: Int, // 1-5
    val comentario: String?,
    val dataHora: Date = Date()
) 