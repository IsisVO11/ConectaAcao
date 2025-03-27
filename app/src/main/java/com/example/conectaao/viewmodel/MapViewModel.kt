package com.example.conectaao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conectaao.model.Location
import com.example.conectaao.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MapViewModel : ViewModel() {
    private val _accessibleLocations = MutableStateFlow<List<Location>>(emptyList())
    val accessibleLocations: StateFlow<List<Location>> = _accessibleLocations

    private val _nearbyVolunteers = MutableStateFlow<List<User>>(emptyList())
    val nearbyVolunteers: StateFlow<List<User>> = _nearbyVolunteers

    init {
        loadAccessibleLocations()
        loadNearbyVolunteers()
    }

    private fun loadAccessibleLocations() {
        viewModelScope.launch {
            // Simulação de carregamento de dados
            _accessibleLocations.value = listOf(
                Location(-23.5505, -46.6333),
                Location(-23.5510, -46.6340)
            )
        }
    }

    private fun loadNearbyVolunteers() {
        viewModelScope.launch {
            // Simulação de carregamento de dados
            _nearbyVolunteers.value = listOf(
                User(id = "1", name = "Voluntário 1", location = Location(-23.5505, -46.6333)),
                User(id = "2", name = "Voluntário 2", location = Location(-23.5510, -46.6340))
            )
        }
    }
} 