package com.example.conectaao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

data class Post(
    val id: String,
    val authorName: String,
    val content: String,
    val likes: Int,
    val comments: List<String>
)

class CommunityViewModel : ViewModel() {
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            // Simulação de carregamento de dados
            _posts.value = listOf(
                Post(id = "1", authorName = "Usuário 1", content = "Conteúdo do post 1", likes = 10, comments = listOf("Comentário 1")),
                Post(id = "2", authorName = "Usuário 2", content = "Conteúdo do post 2", likes = 5, comments = listOf("Comentário 2"))
            )
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            _posts.value = _posts.value.map { post ->
                if (post.id == postId) post.copy(likes = post.likes + 1) else post
            }
        }
    }

    fun createPost(content: String) {
        viewModelScope.launch {
            val newPost = Post(
                id = (_posts.value.size + 1).toString(),
                authorName = "Novo Usuário",
                content = content,
                likes = 0,
                comments = emptyList()
            )
            _posts.value = _posts.value + newPost
        }
    }
} 