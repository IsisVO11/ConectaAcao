package com.example.conectaao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.conectaao.data.SampleData
import com.example.conectaao.model.Comment
import com.example.conectaao.model.Post
import com.example.conectaao.ui.components.CommentItem
import com.example.conectaao.ui.components.PostItem
import com.example.conectaao.ui.theme.BlueStart
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostDetailScreen(
    navController: NavController,
    postId: String
) {
    var commentText by remember { mutableStateOf("") }
    
    // Encontrar o post nos dados de exemplo
    val post = remember {
        val originalPost = SampleData.posts.find { it.id == postId }
        originalPost?.let {
            Post(
                id = it.id,
                authorName = it.authorName,
                authorImage = "",
                content = it.content,
                timestamp = it.timestamp,
                likes = it.likes,
                comments = it.comments,
                timeAgo = getTimeAgoString(it.timestamp),
                imageResId = it.imageResId
            )
        } ?: Post(id = "0", authorName = "Desconhecido", content = "Post não encontrado")
    }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Detalhes do Post",
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
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Campo de entrada para novo comentário
                    OutlinedTextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text("Adicionar um comentário...") },
                        modifier = Modifier.weight(1f),
                        maxLines = 3
                    )
                    
                    IconButton(
                        onClick = {
                            // Implementar adição de comentário
                            commentText = ""
                        }
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.Send,
                            contentDescription = "Enviar",
                            tint = BlueStart
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Mostrar o post completo
            item {
                PostItem(post = post)
                
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                
                Text(
                    text = "Comentários (${post.comments.size})",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(16.dp)
                )
            }
            
            // Lista de comentários
            items(post.comments) { comment ->
                CommentItem(comment = comment)
            }
            
            // Espaço no final para garantir que o último comentário não fique sob o campo de texto
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
}

// Função auxiliar para calcular o tempo decorrido
private fun getTimeAgoString(timestamp: Long): String {
    val currentTime = System.currentTimeMillis()
    val difference = currentTime - timestamp
    
    return when {
        difference < 60 * 1000 -> "Agora mesmo"
        difference < 60 * 60 * 1000 -> "${difference / (60 * 1000)} minutos atrás"
        difference < 24 * 60 * 60 * 1000 -> "${difference / (60 * 60 * 1000)} horas atrás"
        difference < 48 * 60 * 60 * 1000 -> "Ontem"
        else -> {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale("pt", "BR"))
            sdf.format(Date(timestamp))
        }
    }
} 