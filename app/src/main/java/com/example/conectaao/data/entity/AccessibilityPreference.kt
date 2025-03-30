package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "accessibility_preferences",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["usuarioId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("usuarioId", unique = true)]
)
data class AccessibilityPreference(
    @PrimaryKey val usuarioId: Long,
    val tamanhoFonte: Float = 1.0f, // multiplicador do tamanho padrão
    val altoContraste: Boolean = false,
    val narracaoVoz: Boolean = false,
    val velocidadeNarracao: Float = 1.0f,
    val outrasConfiguracoes: String? // JSON com outras configurações
) 