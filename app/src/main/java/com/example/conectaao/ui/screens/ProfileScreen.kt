package com.example.conectaao.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.conectaao.R
import com.example.conectaao.ui.theme.BlueStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    // Estado do usuário atual (simulado)
    val userName = "Pedro Costa"
    val userEmail = "pedro.costa@email.com"
    val isVolunteer = true
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Meu Perfil",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueStart
                ),
                actions = {
                    IconButton(onClick = { /* Implementar configurações */ }) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Configurações",
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Foto de perfil
            Box(
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 24.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.profile_placeholder),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                
                FloatingActionButton(
                    onClick = { /* Implementar alteração de foto */ },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .size(40.dp),
                    containerColor = BlueStart,
                    contentColor = Color.White
                ) {
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Alterar foto",
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
            
            // Nome e email
            Text(
                text = userName,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Text(
                text = userEmail,
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
            )
            
            // Badge de voluntário
            if (isVolunteer) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = BlueStart.copy(alpha = 0.1f)
                    ),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_volunteer),
                            contentDescription = null,
                            tint = BlueStart,
                            modifier = Modifier.size(24.dp)
                        )
                        
                        Spacer(modifier = Modifier.width(8.dp))
                        
                        Text(
                            text = "Voluntário(a) Ativo",
                            color = BlueStart,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
            
            // Opções de perfil
            ProfileOption(
                title = "Histórico de Atendimentos",
                description = "Veja os atendimentos que você realizou",
                onClick = { navController.navigate("service_history") }
            )
            
            ProfileOption(
                title = "Histórico Voluntário",
                description = "Veja seu histórico como voluntário",
                onClick = { navController.navigate("volunteer_history") },
                isVisible = isVolunteer
            )
            
            ProfileOption(
                title = "Disponibilidade",
                description = "Defina seus horários de disponibilidade",
                onClick = { navController.navigate("availability") },
                isVisible = isVolunteer
            )
            
            ProfileOption(
                title = "Alterar Dados",
                description = "Altere seus dados pessoais e senha",
                onClick = { navController.navigate("edit_profile") }
            )
            
            // Botão de logout
            Button(
                onClick = { 
                    // Implementar logout e navegar para a tela de login
                    navController.navigate("login") {
                        popUpTo(0) { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red.copy(alpha = 0.8f)
                )
            ) {
                Text("Sair da Conta")
            }
        }
    }
}

@Composable
fun ProfileOption(
    title: String,
    description: String,
    onClick: () -> Unit,
    isVisible: Boolean = true
) {
    if (isVisible) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .clickable { onClick() },
            shape = RoundedCornerShape(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier.weight(1f)
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = description,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
                
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_right),
                    contentDescription = null,
                    tint = BlueStart
                )
            }
        }
    }
} 