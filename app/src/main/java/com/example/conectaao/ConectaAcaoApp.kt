package com.example.conectaao

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ConectaAcaoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        
        if (!isNetworkAvailable()) {
            Log.e("ConectaAcaoApp", "Sem conexão de rede. O Firebase pode não funcionar corretamente.")
            Toast.makeText(
                this, 
                "Sem conexão de internet. Algumas funcionalidades podem não funcionar corretamente.", 
                Toast.LENGTH_LONG
            ).show()
        }
        
        initializeFirebase()
    }
    
    private fun initializeFirebase() {
        try {
            // Verificar se o Firebase já está inicializado
            if (FirebaseApp.getApps(this).isNotEmpty()) {
                Log.d("ConectaAcaoApp", "Firebase já inicializado anteriormente")
                configureFirestoreSettings()
                return
            }
            
            Log.d("ConectaAcaoApp", "Inicializando Firebase com configuração explícita")
            
            // Primeiro tente a inicialização padrão
            try {
                FirebaseApp.initializeApp(this)
                Log.d("ConectaAcaoApp", "Firebase inicializado com configuração padrão")
            } catch (e: Exception) {
                Log.e("ConectaAcaoApp", "Falha na inicialização padrão, tentando configuração explícita: ${e.message}")
                
                // Se falhar, use a configuração explícita
                val options = FirebaseOptions.Builder()
                    .setApplicationId("1:490628391435:android:6857cf2ac24ee5cf091d0e")
                    .setApiKey("AIzaSyBGh3q0lOjO3WJFp_ZqVRvStGpSiKNTBPQ")
                    .setProjectId("conectaacao-c4024")
                    .setStorageBucket("conectaacao-c4024.firebasestorage.app")
                    .build()
                
                FirebaseApp.initializeApp(this, options)
                Log.d("ConectaAcaoApp", "Firebase inicializado com configurações explícitas")
            }
            
            // Configure o Firestore após inicializar o Firebase
            configureFirestoreSettings()
            
            // Verificar se o Firebase está funcionando corretamente
            val firestore = FirebaseFirestore.getInstance()
            val auth = FirebaseAuth.getInstance()
            
            Log.d("ConectaAcaoApp", "Firebase inicializado com sucesso: Auth=${auth != null}, Firestore=${firestore != null}")
        } catch (e: Exception) {
            Log.e("ConectaAcaoApp", "Erro ao inicializar Firebase: ${e.message}", e)
            Toast.makeText(this, "Erro na configuração do Firebase: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
    
    private fun configureFirestoreSettings() {
        try {
            val firestore = FirebaseFirestore.getInstance()
            val settings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
            firestore.firestoreSettings = settings
            Log.d("ConectaAcaoApp", "Configurações do Firestore aplicadas com sucesso")
        } catch (e: Exception) {
            Log.e("ConectaAcaoApp", "Erro ao configurar Firestore: ${e.message}", e)
        }
    }
    
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
        
        return when {
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
} 