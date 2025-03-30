package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val email: String,
    val senha: String,
    val telefone: String? = null,
    val dataNascimento: Date? = null,
    val fotoPerfil: String? = null,
    val tipo: String, // "PCD", "IDOSO", "VOLUNTARIO", "INSTITUICAO"
    val dataCadastro: Date = Date(),
    val ultimoAcesso: Date = Date()
) 