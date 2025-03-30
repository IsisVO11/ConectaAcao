package com.example.conectaao.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.conectaao.model.Post
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CommunityViewModel : ViewModel() {
    private val firestore = FirebaseFirestore.getInstance()
    
    // Definição de um modelo interno para o Firestore
    private data class FirestorePost(
        val id: String = "",
        val authorName: String = "",
        val content: String = "",
        val likes: Int = 0,
        val comments: List<String> = emptyList(),
        val timestamp: Long = System.currentTimeMillis()
    )
    
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
                    val firestorePost = doc.toObject(FirestorePost::class.java)?.copy(id = doc.id)
                    firestorePost?.let {
                        Post(
                            id = it.id,
                            authorName = it.authorName,
                            content = it.content,
                            timestamp = it.timestamp,
                            likes = it.likes,
                            comments = emptyList()
                        )
                    }
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
                val newFirestorePost = FirestorePost(
                    content = content,
                    authorName = authorName,
                    likes = 0,
                    comments = emptyList()
                )
                
                val docRef = firestore.collection("posts").document()
                docRef.set(newFirestorePost).await()
                
                // Update local state
                val newPost = Post(
                    id = docRef.id,
                    authorName = authorName,
                    content = content,
                    timestamp = System.currentTimeMillis(),
                    likes = 0,
                    comments = emptyList()
                )
                _posts.value = listOf(newPost) + _posts.value
            } catch (e: Exception) {
                // Handle error appropriately
                e.printStackTrace()
            }
        }
    }
} 