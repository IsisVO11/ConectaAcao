package com.example.conectaao.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseNetworkException
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth by lazy { 
        try {
            FirebaseAuth.getInstance()
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Erro ao obter instância do FirebaseAuth: ${e.message}", e)
            throw e
        }
    }
    
    private val firestore: FirebaseFirestore by lazy { 
        try {
            FirebaseFirestore.getInstance()
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Erro ao obter instância do FirebaseFirestore: ${e.message}", e)
            throw e
        }
    }
    
    private val _authState = MutableStateFlow<AuthState>(AuthState.Initial)
    val authState: StateFlow<AuthState> = _authState
    
    init {
        try {
            // Verificar se já existe um usuário logado
            val currentUser = auth.currentUser
            if (currentUser != null) {
                _authState.value = AuthState.Success
            }
        } catch (e: Exception) {
            Log.e("AuthViewModel", "Erro na inicialização do AuthViewModel: ${e.message}", e)
        }
    }
    
    fun signIn(email: String, password: String) {
        viewModelScope.launch {
            try {
                _authState.value = AuthState.Loading
                Log.d("AuthViewModel", "Tentando fazer login com email: $email")
                
                if (email.isBlank() || password.isBlank()) {
                    _authState.value = AuthState.Error("Email e senha são obrigatórios")
                    return@launch
                }
                
                auth.signInWithEmailAndPassword(email, password).await()
                Log.d("AuthViewModel", "Login realizado com sucesso")
                _authState.value = AuthState.Success
            } catch (e: FirebaseAuthInvalidUserException) {
                Log.e("AuthViewModel", "Usuário não encontrado: ${e.message}")
                _authState.value = AuthState.Error("Usuário não encontrado. Verifique seu email.")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.e("AuthViewModel", "Credenciais inválidas: ${e.message}")
                _authState.value = AuthState.Error("Senha incorreta. Tente novamente.")
            } catch (e: FirebaseNetworkException) {
                Log.e("AuthViewModel", "Erro de conexão: ${e.message}")
                _authState.value = AuthState.Error("Erro de conexão. Verifique sua internet.")
            } catch (e: FirebaseException) {
                if (e.message?.contains("CONFIGURATION_NOT_FOUND") == true) {
                    Log.e("AuthViewModel", "Erro de configuração do Firebase: ${e.message}")
                    _authState.value = AuthState.Error("Erro na configuração do Firebase. Reinstale o aplicativo ou contate o suporte.")
                } else {
                    Log.e("AuthViewModel", "Erro do Firebase: ${e.message}")
                    _authState.value = AuthState.Error("Erro no serviço Firebase: ${e.message}")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Erro ao fazer login: ${e.message}", e)
                _authState.value = AuthState.Error(e.message ?: "Erro ao fazer login")
            }
        }
    }
    
    fun signUp(email: String, password: String, name: String, isVolunteer: Boolean) {
        viewModelScope.launch {
            try {
                Log.d("AuthViewModel", "Iniciando registro com email: $email")
                _authState.value = AuthState.Loading
                
                if (email.isBlank() || password.isBlank() || name.isBlank()) {
                    _authState.value = AuthState.Error("Todos os campos são obrigatórios")
                    return@launch
                }
                
                // Criar usuário no Firebase Auth
                val result = auth.createUserWithEmailAndPassword(email, password).await()
                
                Log.d("AuthViewModel", "Usuário criado com sucesso")
                result.user?.let { user ->
                    try {
                        val userData = hashMapOf(
                            "name" to name,
                            "email" to email,
                            "isVolunteer" to isVolunteer,
                            "tipo" to if (isVolunteer) "VOLUNTARIO" else "USUARIO", // Para compatibilidade com Room
                            "createdAt" to System.currentTimeMillis()
                        )
                        
                        Log.d("AuthViewModel", "Salvando dados do usuário no Firestore: $userData")
                        
                        firestore.collection("users")
                            .document(user.uid)
                            .set(userData)
                            .await()
                        
                        Log.d("AuthViewModel", "Dados do usuário salvos com sucesso")
                    } catch (e: Exception) {
                        Log.e("AuthViewModel", "Erro ao salvar dados do usuário: ${e.message}", e)
                        // Não interrompa o fluxo se falhar apenas a parte do Firestore
                    }
                }
                
                _authState.value = AuthState.Success
            } catch (e: FirebaseAuthUserCollisionException) {
                Log.e("AuthViewModel", "Email já em uso: ${e.message}")
                _authState.value = AuthState.Error("Este email já está em uso. Tente outro.")
            } catch (e: FirebaseAuthInvalidCredentialsException) {
                Log.e("AuthViewModel", "Email inválido: ${e.message}")
                _authState.value = AuthState.Error("Email inválido. Verifique se digitou corretamente.")
            } catch (e: FirebaseNetworkException) {
                Log.e("AuthViewModel", "Erro de conexão: ${e.message}")
                _authState.value = AuthState.Error("Erro de conexão. Verifique sua internet.")
            } catch (e: FirebaseException) {
                if (e.message?.contains("CONFIGURATION_NOT_FOUND") == true) {
                    Log.e("AuthViewModel", "Erro de configuração do Firebase: ${e.message}")
                    _authState.value = AuthState.Error("Erro na configuração do Firebase. Reinstale o aplicativo ou contate o suporte.")
                } else {
                    Log.e("AuthViewModel", "Erro do Firebase: ${e.message}")
                    _authState.value = AuthState.Error("Erro no serviço Firebase: ${e.message}")
                }
            } catch (e: Exception) {
                Log.e("AuthViewModel", "Erro no registro: ${e.message}", e)
                _authState.value = AuthState.Error("Erro ao criar conta: ${e.message}")
            }
        }
    }
}

sealed class AuthState {
    object Initial : AuthState()
    object Loading : AuthState()
    object Success : AuthState()
    data class Error(val message: String) : AuthState()
} 