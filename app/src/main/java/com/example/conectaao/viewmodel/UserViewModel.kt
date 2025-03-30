package com.example.conectaao.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.conectaao.data.entity.User
import com.example.conectaao.data.entity.UserProfile
import com.example.conectaao.data.relation.UserWithProfile
import com.example.conectaao.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application)
    
    private val _currentUser = MutableStateFlow<UserWithProfile?>(null)
    val currentUser: StateFlow<UserWithProfile?> = _currentUser.asStateFlow()

    private val _voluntarios = MutableStateFlow<List<User>>(emptyList())
    val voluntarios: StateFlow<List<User>> = _voluntarios.asStateFlow()

    private val _pcds = MutableStateFlow<List<User>>(emptyList())
    val pcds: StateFlow<List<User>> = _pcds.asStateFlow()

    private val _idosos = MutableStateFlow<List<User>>(emptyList())
    val idosos: StateFlow<List<User>> = _idosos.asStateFlow()

    fun loadUserWithProfile(userId: Long) {
        viewModelScope.launch {
            repository.getUserWithProfile(userId).collect { userWithProfile ->
                _currentUser.value = userWithProfile
            }
        }
    }

    fun loadVoluntarios() {
        viewModelScope.launch {
            repository.getVoluntarios().collect { users ->
                _voluntarios.value = users
            }
        }
    }

    fun loadPcDs() {
        viewModelScope.launch {
            repository.getPcDs().collect { users ->
                _pcds.value = users
            }
        }
    }

    fun loadIdosos() {
        viewModelScope.launch {
            repository.getIdosos().collect { users ->
                _idosos.value = users
            }
        }
    }

    fun createUser(
        nome: String,
        email: String,
        senha: String,
        telefone: String?,
        tipo: String,
        endereco: String?,
        cidade: String?,
        estado: String?
    ) {
        viewModelScope.launch {
            val user = User(
                nome = nome,
                email = email,
                senha = senha, // Idealmente, você deveria usar hash aqui
                telefone = telefone,
                dataNascimento = null,
                fotoPerfil = null,
                tipo = tipo
            )
            
            val profile = UserProfile(
                userId = 0, // Será substituído pelo ID real
                endereco = endereco,
                cidade = cidade,
                estado = estado,
                cep = null,
                latitude = null,
                longitude = null,
                biografia = null,
                necessidadesEspecificas = null,
                habilidades = null,
                disponibilidade = null
            )
            
            repository.createUser(user, profile)
        }
    }
} 