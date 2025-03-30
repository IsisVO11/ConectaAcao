package com.example.conectaao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Send
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
import com.example.conectaao.ui.theme.BlueStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentScreen(
    navController: NavController,
    postId: String
) {
    var commentText by remember { mutableStateOf("") }
    val post = remember { 
        SampleData.posts.find { it.id == postId } 
            ?: Post(id = "0", authorName = "Desconhecido", content = "Post não encontrado")
    }
    
    // Lista mutável para demonstrar a adição de comentários
    var comments by remember { mutableStateOf(post.comments) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { 
                    Text(
                        text = "Comentários", 
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
                    
                    Spacer(modifier = Modifier.width(8.dp))
                    
                    Button(
                        onClick = {
                            if (commentText.isNotBlank()) {
                                // Criar um novo comentário e adicionar à lista
                                val newComment = Comment(
                                    id = "${System.currentTimeMillis()}",
                                    authorName = "Você",
                                    content = commentText,
                                    timestamp = System.currentTimeMillis()
                                )
                                
                                comments = comments + newComment
                                commentText = ""
                            }
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueStart
                        )
                    ) {
                        Text("Enviar")
                    }
                }
            }
        }
    ) { innerPadding ->
        if (comments.isEmpty()) {
            // Exibir mensagem quando não há comentários
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Nenhum comentário ainda. Seja o primeiro a comentar!",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        } else {
            // Lista de comentários
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(comments) { comment ->
                    CommentItem(comment = comment)
                }
                
                // Espaço no final para garantir que o último comentário não fique sob o campo de texto
                item {
                    Spacer(modifier = Modifier.height(80.dp))
                }
            }
        }
    }
} 