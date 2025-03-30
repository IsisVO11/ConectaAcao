package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "assistances",
    foreignKeys = [
        ForeignKey(
            entity = Request::class,
            parentColumns = ["id"],
            childColumns = ["solicitacaoId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["voluntarioId"],
            onDelete = ForeignKey.SET_NULL
        )
    ],
    indices = [Index("solicitacaoId"), Index("voluntarioId")]
)
data class Assistance(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val solicitacaoId: Long,
    val voluntarioId: Long?,
    val dataHoraInicio: Date?,
    val dataHoraFim: Date?,
    val status: String, // "EM_ANDAMENTO", "CONCLUIDO", "CANCELADO"
    val avaliacaoSolicitante: Int?, // 1-5
    val avaliacaoVoluntario: Int?, // 1-5
    val comentarios: String?
) 