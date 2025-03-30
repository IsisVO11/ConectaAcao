package com.example.conectaao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.conectaao.data.SampleData
import com.example.conectaao.ui.components.UserListItem
import com.example.conectaao.ui.theme.BlueStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VolunteersScreen(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Voluntários",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueStart
                ),
                actions = {
                    IconButton(onClick = { /* Implementar busca */ }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "Buscar",
                            tint = Color.White
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Filtros
            FilterChips(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            
            // Lista de voluntários
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(SampleData.volunteers) { volunteer ->
                    UserListItem(
                        user = volunteer,
                        onClick = {
                            // Navegar para a tela de detalhes do voluntário
                            navController.navigate("volunteer_detail/${volunteer.id}")
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(modifier: Modifier = Modifier) {
    var selectedFilter by remember { mutableStateOf<String?>(null) }
    
    ScrollableRow(
        modifier = modifier
    ) {
        val filters = listOf("Todos", "Acompanhamento", "Transporte", "Saúde", "Educação")
        
        filters.forEach { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { selectedFilter = if (selectedFilter == filter) null else filter },
                label = { Text(filter) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}

@Composable
fun ScrollableRow(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    androidx.compose.foundation.layout.Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        content()
    }
} 