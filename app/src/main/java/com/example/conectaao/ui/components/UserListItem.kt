package com.example.conectaao.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.conectaao.R
import com.example.conectaao.model.User

@Composable
fun UserListItem(
    user: User,
    onClick: (User) -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onClick(user) }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Avatar do usuário
            Image(
                painter = painterResource(id = R.drawable.placeholder_avatar),
                contentDescription = "Avatar de ${user.name}",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Informações do usuário
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    text = user.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Especialidades
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = user.specialties.joinToString(", "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Avaliação e distância
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Avaliação
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Avaliação",
                        tint = Color(0xFFFFB800),
                        modifier = Modifier.size(16.dp)
                    )
                    
                    Spacer(modifier = Modifier.width(4.dp))
                    
                    Text(
                        text = user.rating.toString(),
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    Spacer(modifier = Modifier.width(16.dp))
                    
                    // Distância
                    Text(
                        text = "A ${user.distance} km",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                // Disponibilidade
                val disponibilidadeText = when (user.availability) {
                    com.example.conectaao.model.Availability.MORNINGS -> "Disponível pela manhã"
                    com.example.conectaao.model.Availability.AFTERNOONS -> "Disponível à tarde"
                    com.example.conectaao.model.Availability.EVENINGS -> "Disponível à noite"
                    com.example.conectaao.model.Availability.WEEKENDS -> "Disponível nos finais de semana"
                    com.example.conectaao.model.Availability.WEEKDAYS -> "Disponível nos dias de semana"
                    com.example.conectaao.model.Availability.FULL_TIME -> "Disponível em tempo integral"
                    else -> "Disponibilidade não especificada"
                }
                
                Text(
                    text = disponibilidadeText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.tertiary
                )
            }
            
            Spacer(modifier = Modifier.width(16.dp))
            
            // Botão para contatar
            IconButton(
                onClick = { /* Implementar ação de contatar */ }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_contact),
                    contentDescription = "Contatar voluntário",
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
} 