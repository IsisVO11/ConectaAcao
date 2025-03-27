package com.example.conectaao.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.conectaao.viewmodel.CommunityViewModel

@Composable
fun CommunityScreen(
    viewModel: CommunityViewModel = viewModel()
) {
    val posts by viewModel.posts.collectAsState()
    var newPostText by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Lista de posts
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(posts) { post ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            text = post.authorName,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(post.content)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            TextButton(onClick = { viewModel.likePost(post.id) }) {
                                Text("${post.likes} Curtidas")
                            }
                            TextButton(onClick = { /* TODO: Implementar comentários */ }) {
                                Text("${post.comments.size} Comentários")
                            }
                        }
                    }
                }
            }
        }
        
        // Campo para novo post
        OutlinedTextField(
            value = newPostText,
            onValueChange = { newPostText = it },
            label = { Text("Compartilhe algo...") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        
        Button(
            onClick = { 
                viewModel.createPost(newPostText)
                newPostText = ""
            },
            modifier = Modifier.align(Alignment.End),
            enabled = newPostText.isNotEmpty()
        ) {
            Text("Publicar")
        }
    }
} 