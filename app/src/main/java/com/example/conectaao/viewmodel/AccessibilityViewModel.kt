package com.example.conectaao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AccessibilityViewModel : ViewModel() {
    private val _recognizedText = MutableStateFlow("")
    val recognizedText: StateFlow<String> = _recognizedText

    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking

    fun startTextRecognition() {
        viewModelScope.launch {
            // Simulação de reconhecimento de texto
            _recognizedText.value = "Texto reconhecido da câmera"
        }
    }

    fun speakText(text: String) {
        viewModelScope.launch {
            _isSpeaking.value = true
            // Simulação de conversão de texto para fala
            kotlinx.coroutines.delay(2000) // Simula o tempo de fala
            _isSpeaking.value = false
        }
    }
}

open class ViewModel {

}
