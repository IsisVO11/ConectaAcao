package com.example.conectaao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

data class Post(
    val id: String = "",
    val authorName: String = "",
    val content: String = "",
    val likes: Int = 0,
    val comments: List<String> = emptyList(),
    val timestamp: Long = System.currentTimeMillis()
)

class CommunityViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    
    private val _posts = MutableStateFlow<List<Post>>(emptyList())
    val posts: StateFlow<List<Post>> = _posts

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            try {
                val snapshot = firestore.collection("posts")
                    .orderBy("timestamp", com.google.firebase.firestore.Query.Direction.DESCENDING)
                    .get()
                    .await()
                
                _posts.value = snapshot.documents.mapNotNull { doc ->
                    doc.toObject(Post::class.java)?.copy(id = doc.id)
                }
            } catch (e: Exception) {
                // Handle error appropriately
                e.printStackTrace()
            }
        }
    }

    fun likePost(postId: String) {
        viewModelScope.launch {
            try {
                val postRef = firestore.collection("posts").document(postId)
                firestore.runTransaction { transaction ->
                    val snapshot = transaction.get(postRef)
                    val currentLikes = snapshot.getLong("likes")?.toInt() ?: 0
                    transaction.update(postRef, "likes", currentLikes + 1)
                }.await()
                
                // Update local state
                _posts.value = _posts.value.map { post ->
                    if (post.id == postId) post.copy(likes = post.likes + 1) else post
                }
            } catch (e: Exception) {
                // Handle error appropriately
                e.printStackTrace()
            }
        }
    }

    fun createPost(content: String, authorName: String) {
        viewModelScope.launch {
            try {
                val newPost = Post(
                    content = content,
                    authorName = authorName,
                    likes = 0,
                    comments = emptyList()
                )
                
                val docRef = firestore.collection("posts").document()
                docRef.set(newPost).await()
                
                // Update local state
                _posts.value = listOf(newPost.copy(id = docRef.id)) + _posts.value
            } catch (e: Exception) {
                // Handle error appropriately
                e.printStackTrace()
            }
        }
    }
} 