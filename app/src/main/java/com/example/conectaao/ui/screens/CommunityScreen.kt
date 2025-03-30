package com.example.conectaao.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.conectaao.data.SampleData
import com.example.conectaao.ui.components.PostItem
import com.example.conectaao.ui.theme.BlueStart

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(navController: NavController) {
    // Scaffold para a estrutura básica da tela
    Scaffold(
        topBar = {
            // TopAppBar personalizada
            TopAppBar(
                title = { 
                    Text(
                        text = "Comunidade", 
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    ) 
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = BlueStart
                )
            )
        },
        floatingActionButton = {
            // FAB para criar novo post
            FloatingActionButton(
                onClick = { 
                    // Navegar para a tela de criação de post
                    navController.navigate("create_post")
                },
                containerColor = BlueStart,
                contentColor = Color.White
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Criar Post"
                )
            }
        }
    ) { innerPadding ->
        // Conteúdo principal - lista de posts
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(SampleData.posts) { post ->
                PostItem(
                    post = post,
                    onPostClick = { 
                        // Navegar para a tela de detalhes do post
                        navController.navigate("post_detail/${post.id}")
                    },
                    onCommentClick = {
                        // Navegar para a tela de comentários
                        navController.navigate("comments/${post.id}")
                    },
                    onLikeClick = { 
                        // Implementar funcionalidade de curtir post 
                    }
                )
                
                // Separador entre os posts
                if (post != SampleData.posts.last()) {
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        color = Color.LightGray
                    )
                }
            }
            
            // Adicionar espaço no final para o FAB não cobrir o último item
            item {
                Spacer(modifier = Modifier.height(80.dp))
            }
        }
    }
} 