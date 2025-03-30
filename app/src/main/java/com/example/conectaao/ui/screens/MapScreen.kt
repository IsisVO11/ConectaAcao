package com.example.conectaao.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.conectaao.R
import com.example.conectaao.data.SampleData
import com.example.conectaao.model.MarkerType
import com.example.conectaao.model.Place
import com.example.conectaao.ui.components.MarkerIconButton
import com.example.conectaao.ui.components.bitmapDescriptorFromVector
import com.example.conectaao.ui.theme.BlueStart
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapScreen(navController: NavController) {
    // Dados de exemplo para o mapa
    val places = remember { SampleData.places }
    
    // Estado do mapa
    var selectedPlace by remember { mutableStateOf<Place?>(null) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(-23.5505, -46.6333), 13f) // São Paulo
    }
    
    Box(modifier = Modifier.fillMaxSize()) {
        // Mapa do Google Maps
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = MapProperties(isMyLocationEnabled = true),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = false,
                myLocationButtonEnabled = false
            )
        ) {
            // Adicionar marcadores para cada local
            places.forEach { place ->
                Marker(
                    state = MarkerState(position = place.location),
                    title = place.name,
                    snippet = place.description,
                    icon = bitmapDescriptorFromVector(
                        context = androidx.compose.ui.platform.LocalContext.current,
                        resourceId = when (place.type) {
                            MarkerType.HOSPITAL -> R.drawable.ic_marker_hospital
                            MarkerType.POLICE -> R.drawable.ic_marker_police
                            MarkerType.FIRE_STATION -> R.drawable.ic_marker_fire
                            MarkerType.ACCESSIBLE_LOCATION -> R.drawable.ic_marker_accessible
                            MarkerType.VOLUNTEER -> R.drawable.ic_marker_volunteer
                            else -> R.drawable.ic_marker_default
                        }
                    ),
                    onClick = {
                        selectedPlace = place
                        cameraPositionState.position = CameraPosition.fromLatLngZoom(place.location, 15f)
                        true
                    }
                )
            }
        }
        
        // Barra superior com título
        TopAppBar(
            title = { 
                Text(
                    text = "ConectaAção",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = BlueStart
            ),
            navigationIcon = {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar",
                        tint = Color.White
                    )
                }
            }
        )
        
        // Botões de navegação (serão substituídos pelo bottom navigation na MainActivity)
        Row(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .background(Color.White)
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            IconButton(onClick = { /* Atual */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_map_active),
                    contentDescription = "Mapa",
                    tint = BlueStart
                )
            }
            
            IconButton(onClick = { /* Ir para voluntários */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_volunteer),
                    contentDescription = "Voluntários",
                    tint = Color.Gray
                )
            }
            
            IconButton(onClick = { /* Ir para comunidade */ }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_community),
                    contentDescription = "Comunidade",
                    tint = Color.Gray
                )
            }
        }
        
        // FAB para localização atual
        FloatingActionButton(
            onClick = { /* Implementar foco na localização atual */ },
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(16.dp),
            containerColor = Color.White
        ) {
            Icon(
                imageVector = Icons.Default.MyLocation,
                contentDescription = "Minha Localização",
                tint = Color.Black
            )
        }
        
        // Card com informações do local selecionado
        selectedPlace?.let { place ->
            Card(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(bottom = 64.dp, start = 16.dp, end = 16.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    Text(
                        text = place.name,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    
                    Text(
                        text = place.address,
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    if (place.isAccessible) {
                        Spacer(modifier = Modifier.height(8.dp))
                        
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_accessible),
                                contentDescription = null,
                                tint = BlueStart,
                                modifier = Modifier.size(16.dp)
                            )
                            
                            Spacer(modifier = Modifier.width(4.dp))
                            
                            Text(
                                text = "Acessível",
                                style = MaterialTheme.typography.bodySmall,
                                color = BlueStart
                            )
                        }
                    }
                    
                    Spacer(modifier = Modifier.height(16.dp))
                    
                    Button(
                        onClick = { 
                            // Navegar para tela de detalhes do local usando navController
                            navController.navigate("place_detail/${place.id}")
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueStart
                        )
                    ) {
                        Text("Como Chegar")
                    }
                }
            }
        }
    }
}