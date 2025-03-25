package com.example.conectaao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.conectaao.ui.screens.*
import com.example.conectaao.ui.theme.ConectaAçãoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ConectaAçãoTheme {
                val navController = rememberNavController()
                
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    bottomBar = {
                        NavigationBar {
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate("map") },
                                icon = { Icon(Icons.Default.Map, "Mapa") },
                                label = { Text("Mapa") }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate("volunteers") },
                                icon = { Icon(Icons.Default.People, "Voluntários") },
                                label = { Text("Voluntários") }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate("community") },
                                icon = { Icon(Icons.Default.Forum, "Comunidade") },
                                label = { Text("Comunidade") }
                            )
                            NavigationBarItem(
                                selected = false,
                                onClick = { navController.navigate("profile") },
                                icon = { Icon(Icons.Default.Person, "Perfil") },
                                label = { Text("Perfil") }
                            )
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "login",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("login") { LoginScreen(navController) }
                        composable("map") { MapScreen() }
                        composable("volunteers") { VolunteersScreen() }
                        composable("community") { CommunityScreen() }
                        composable("profile") { ProfileScreen() }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ConectaAçãoTheme {
        Greeting("Android")
    }
}