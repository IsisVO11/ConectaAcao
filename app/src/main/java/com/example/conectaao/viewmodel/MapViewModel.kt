package com.example.conectaao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conectaao.model.Location
import com.example.conectaao.model.User
import com.example.conectaao.model.AccessibleLocation
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class MapViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    
    private val _accessibleLocations = MutableStateFlow<List<AccessibleLocation>>(emptyList())
    val accessibleLocations: StateFlow<List<AccessibleLocation>> = _accessibleLocations

    private val _nearbyVolunteers = MutableStateFlow<List<User>>(emptyList())
    val nearbyVolunteers: StateFlow<List<User>> = _nearbyVolunteers

    init {
        loadAccessibleLocations()
        loadNearbyVolunteers()
    }

    private fun loadAccessibleLocations() {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("accessible_locations")
                    .get()
                    .await()
                
                _accessibleLocations.value = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(AccessibleLocation::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                // Handle error appropriately
                e.printStackTrace()
            }
        }
    }

    private fun loadNearbyVolunteers() {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("users")
                    .whereEqualTo("isVolunteer", true)
                    .get()
                    .await()
                
                _nearbyVolunteers.value = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(User::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                // Handle error appropriately
                e.printStackTrace()
            }
        }
    }
} 