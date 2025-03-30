package com.example.conectaao.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Comment
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.conectaao.R
import com.example.conectaao.model.Post
import com.example.conectaao.ui.theme.BlueStart
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun PostItem(
    post: Post,
    onPostClick: (() -> Unit)? = null,
    onCommentClick: (() -> Unit)? = null,
    onLikeClick: (() -> Unit)? = null,
    onShareClick: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var isLiked by remember { mutableStateOf(false) }
    
    // Calcular o tempo decorrido desde a postagem
    val timeAgo = getTimeAgoString(post.timestamp)
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { onPostClick?.invoke() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            // Cabeçalho do post com informações do autor
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Avatar do autor
                Image(
                    painter = painterResource(
                        id = if (post.authorImage.isNotEmpty()) {
                            context.resources.getIdentifier(
                                post.authorImage, "drawable", context.packageName
                            )
                        } else {
                            R.drawable.profile_placeholder
                        }
                    ),
                    contentDescription = "Avatar de ${post.authorName}",
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
                
                Spacer(modifier = Modifier.width(12.dp))
                
                Column {
                    Text(
                        text = post.authorName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    
                    Text(
                        text = timeAgo,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(12.dp))
            
            // Conteúdo do post
            Text(
                text = post.content,
                fontSize = 14.sp,
                lineHeight = 20.sp
            )
            
            // Imagem do post (se existir)
            if (post.imageResId != null && post.imageResId != 0) {
                Spacer(modifier = Modifier.height(12.dp))
                
                Image(
                    painter = painterResource(id = post.imageResId),
                    contentDescription = "Imagem do post",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            // Contador de curtidas e comentários
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "${post.likes} curtidas",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
                
                Text(
                    text = "${post.comments.size} comentários",
                    fontSize = 12.sp,
                    color = Color.Gray,
                    modifier = Modifier.clickable { onCommentClick?.invoke() }
                )
            }
            
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp)
            )
            
            // Botões de interação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Botão de curtir
                TextButton(
                    onClick = { 
                        isLiked = !isLiked
                        onLikeClick?.invoke() 
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = if (isLiked) BlueStart else Color.Gray
                    )
                ) {
                    Icon(
                        imageVector = if (isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Curtir"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Curtir")
                }
                
                // Botão de comentar
                TextButton(
                    onClick = { onCommentClick?.invoke() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Gray
                    )
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Comment,
                        contentDescription = "Comentar"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Comentar")
                }
                
                // Botão de compartilhar
                TextButton(
                    onClick = { onShareClick?.invoke() },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = Color.Gray
                    )
                ) {
                    Icon(
                        imageVector = Icons.Filled.Share,
                        contentDescription = "Compartilhar"
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(text = "Compartilhar")
                }
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