package com.example.conectaao.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.conectaao.ui.theme.BlueStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostScreen(navController: NavController) {
    var postText by remember { mutableStateOf("") }
    var isSending by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Criar Post", 
                        style = MaterialTheme.typography.titleMedium,
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
                },
                actions = {
                    // Botão de enviar post
                    Button(
                        onClick = {
                            isSending = true
                            // Simulação de envio de post
                            // Em um caso real, isso seria feito via ViewModel
                            focusManager.clearFocus()
                            
                            // Navegar de volta após enviar
                            navController.popBackStack()
                        },
                        enabled = postText.isNotBlank() && !isSending,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = BlueStart,
                            disabledContainerColor = Color.LightGray,
                            disabledContentColor = Color.Gray
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.padding(end = 8.dp)
                    ) {
                        Text("Publicar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            // Campo para digitar o post
            OutlinedTextField(
                value = postText,
                onValueChange = { postText = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f),
                placeholder = { Text("O que você está pensando?") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BlueStart,
                    unfocusedBorderColor = Color.LightGray
                ),
                minLines = 5
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Área de anexos
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = Color(0xFFF5F5F5),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // Botão para adicionar foto da galeria
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Implementar seleção de imagem */ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Image,
                            contentDescription = "Adicionar Imagem",
                            tint = BlueStart,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Galeria",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray
                        )
                    }
                    
                    // Botão para tirar foto
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.clickable { /* Implementar câmera */ }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Camera,
                            contentDescription = "Tirar Foto",
                            tint = BlueStart,
                            modifier = Modifier.size(28.dp)
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "Câmera",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.DarkGray
                        )
                    }
                }
            }
        }
    }
} 