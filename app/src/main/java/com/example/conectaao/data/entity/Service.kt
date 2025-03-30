package com.example.conectaao.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "services")
data class Service(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val nome: String,
    val descricao: String,
    val categoria: String,
    val endereco: String,
    val latitude: Double,
    val longitude: Double,
    val telefone: String?,
    val email: String?,
    val horarioFuncionamento: String?,
    val acessibilidade: String? // JSON ou texto com recursos disponíveis
) 