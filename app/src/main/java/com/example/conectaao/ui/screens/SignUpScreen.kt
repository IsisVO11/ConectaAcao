package com.example.conectaao.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.conectaao.ui.theme.BlueStart
import com.example.conectaao.viewmodel.AuthState
import com.example.conectaao.viewmodel.AuthViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignUpScreen(
    navController: NavController,
    viewModel: AuthViewModel = viewModel()
) {
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isVolunteer by remember { mutableStateOf(false) }
    var skills by remember { mutableStateOf("") }
    
    val scrollState = rememberScrollState()
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    var errorMessage by remember { mutableStateOf<String?>(null) }
    
    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Success -> navController.navigate("map") {
                popUpTo("login") { inclusive = true }
            }
            is AuthState.Error -> {
                errorMessage = (authState as AuthState.Error).message
                Toast.makeText(
                    context, 
                    errorMessage, 
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> {
                errorMessage = null
            }
        }
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Criar Nova Conta") },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueStart,
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))
            
            // Mensagem de erro
            errorMessage?.let {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red.copy(alpha = 0.1f)
                    )
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Erro",
                            tint = Color.Red
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "Erro ao criar conta",
                                color = Color.Red,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = it,
                                color = Color.Red,
                                modifier = Modifier.fillMaxWidth()
                            )
                            
                            if (it.contains("CONFIGURATION_NOT_FOUND")) {
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Problema com a configuração do Firebase. Por favor, reinstale o aplicativo ou contate o suporte.",
                                    color = Color.Red,
                                    fontSize = 12.sp
                                )
                            }
                        }
                    }
                }
            }
            
            Text(
                text = "Preencha seus dados para criar uma nova conta",
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 24.dp)
            )
            
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Nome completo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = authState is AuthState.Error && name.isBlank()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = authState is AuthState.Error && email.isBlank()
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                isError = authState is AuthState.Error && password.isBlank(),
                supportingText = { 
                    if (password.length < 6 && password.isNotEmpty()) {
                        Text("A senha deve ter pelo menos 6 caracteres", color = MaterialTheme.colorScheme.error)
                    }
                }
            )
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Checkbox(
                    checked = isVolunteer,
                    onCheckedChange = { isVolunteer = it }
                )
                Text("Quero ser voluntário")
            }
            
            AnimatedVisibility(visible = isVolunteer) {
                OutlinedTextField(
                    value = skills,
                    onValueChange = { skills = it },
                    label = { Text("Habilidades (separadas por vírgula)") },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Button(
                onClick = { 
                    viewModel.signUp(
                        email = email,
                        password = password,
                        name = name,
                        isVolunteer = isVolunteer
                    )
                },
                enabled = email.isNotEmpty() && password.isNotEmpty() && name.isNotEmpty() && password.length >= 6 && authState !is AuthState.Loading,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
            ) {
                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("CRIAR CONTA", fontSize = 16.sp)
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            TextButton(
                onClick = { navController.navigateUp() }
            ) {
                Text("Já tenho uma conta")
            }
            
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
} 