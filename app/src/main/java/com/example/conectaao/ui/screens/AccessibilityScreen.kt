package com.example.conectaao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.conectaao.viewmodel.AccessibilityViewModel

@Composable
fun AccessibilityScreen(
    viewModel: AccessibilityViewModel = viewModel()
) {
    var text by remember { mutableStateOf("") }
    val recognizedText by viewModel.recognizedText.collectAsState()
    val isSpeaking by viewModel.isSpeaking.collectAsState()
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // OCR
        Button(
            onClick = { viewModel.startTextRecognition() }
        ) {
            Text("Reconhecer Texto da Câmera")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Text-to-Speech
        OutlinedTextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Digite ou cole o texto") },
            modifier = Modifier.fillMaxWidth()
        )
        
        Spacer(modifier = Modifier.height(8.dp))
        
        Button(
            onClick = { viewModel.speakText(text) },
            enabled = text.isNotEmpty() && !isSpeaking
        ) {
            Text(if (isSpeaking) "Falando..." else "Falar Texto")
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Texto reconhecido
        if (recognizedText.isNotEmpty()) {
            Text(
                text = "Texto Reconhecido:",
                style = MaterialTheme.typography.titleMedium
            )
            Text(recognizedText)
        }
    }
} 