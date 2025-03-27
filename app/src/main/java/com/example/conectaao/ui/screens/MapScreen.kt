package com.example.conectaao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.example.conectaao.viewmodel.MapViewModel

@Composable
fun MapScreen(
    viewModel: MapViewModel = viewModel()
) {
    val mapProperties by remember { mutableStateOf(MapProperties(isMyLocationEnabled = true)) }
    val uiSettings by remember { mutableStateOf(MapUiSettings(myLocationButtonEnabled = true)) }
    val locations by viewModel.accessibleLocations.collectAsState()
    val volunteers by viewModel.nearbyVolunteers.collectAsState()
    
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            properties = mapProperties,
            uiSettings = uiSettings,
            cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(LatLng(-23.5505, -46.6333), 10f)
            }
        ) {
            // Marcadores de locais acessíveis
            locations.forEach { location ->
                Marker(
                    state = MarkerState(position = LatLng(location.latitude, location.longitude)),
                    title = location.name,
                    snippet = location.description
                )
            }
            
            // Marcadores de voluntários
            volunteers.forEach { volunteer ->
                Marker(
                    state = MarkerState(position = LatLng(volunteer.location.latitude, volunteer.location.longitude)),
                    title = volunteer.name,
                    snippet = volunteer.skills.joinToString(", ")
                )
            }
        }
        
        // FAB para adicionar novo local acessível
        FloatingActionButton(
            onClick = { /* TODO: Implementar adição de local */ },
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.BottomEnd)
        ) {
            Icon(Icons.Default.Add, "Adicionar local")
        }
    }
} 