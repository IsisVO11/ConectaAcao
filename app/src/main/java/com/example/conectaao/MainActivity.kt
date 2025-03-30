package com.example.conectaao

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.conectaao.ui.screens.CommentScreen
import com.example.conectaao.ui.screens.CommunityScreen
import com.example.conectaao.ui.screens.CreatePostScreen
import com.example.conectaao.ui.screens.LoginScreen
import com.example.conectaao.ui.screens.MapScreen
import com.example.conectaao.ui.screens.PostDetailScreen
import com.example.conectaao.ui.screens.ProfileScreen
import com.example.conectaao.ui.screens.SignUpScreen
import com.example.conectaao.ui.screens.VolunteersScreen
import com.example.conectaao.ui.theme.ConectaAoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ConectaAoTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainNavigation()
                }
            }
        }
    }
}

@Composable
fun MainNavigation() {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    
    // Telas principais para a navegação inferior
    val mainScreens = listOf(
        Screen.Map,
        Screen.Volunteers,
        Screen.Community,
        Screen.Profile
    )
    
    // Verificar se estamos em uma tela principal
    val isMainScreen = currentDestination?.route?.let { route ->
        mainScreens.any { it.route == route }
    } ?: false
    
    Scaffold(
        bottomBar = {
            // Mostrar a barra de navegação apenas nas telas principais
            if (isMainScreen) {
                NavigationBar {
                    mainScreens.forEach { screen ->
                        NavigationBarItem(
                            icon = { 
                                when (screen) {
                                    Screen.Map -> Icon(Icons.Default.Map, contentDescription = stringResource(R.string.map))
                                    Screen.Volunteers -> Icon(
                                        painterResource(id = R.drawable.ic_volunteer),
                                        contentDescription = stringResource(R.string.volunteers)
                                    )
                                    Screen.Community -> Icon(Icons.AutoMirrored.Filled.Chat, contentDescription = stringResource(R.string.community))
                                    Screen.Profile -> Icon(Icons.Default.AccountCircle, contentDescription = stringResource(R.string.profile))
                                }
                            },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    // Evitar múltiplas cópias da mesma tela no back stack
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            // Tela de Login
            composable("login") {
                LoginScreen(navController = navController)
            }
            
            // Tela de Cadastro
            composable("signup") {
                SignUpScreen(navController = navController)
            }
            
            // Telas principais
            composable(Screen.Map.route) {
                MapScreen(navController = navController)
            }
            
            composable(Screen.Volunteers.route) {
                VolunteersScreen(navController = navController)
            }
            
            composable(Screen.Community.route) {
                CommunityScreen(navController = navController)
            }
            
            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
            
            // Telas de detalhes e interações
            composable(
                route = "post_detail/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId") ?: "0"
                PostDetailScreen(navController = navController, postId = postId)
            }
            
            composable(
                route = "comments/{postId}",
                arguments = listOf(navArgument("postId") { type = NavType.StringType })
            ) { backStackEntry ->
                val postId = backStackEntry.arguments?.getString("postId") ?: "0"
                CommentScreen(navController = navController, postId = postId)
            }
            
            composable("create_post") {
                CreatePostScreen(navController = navController)
            }
        }
    }
}

sealed class Screen(val route: String, val resourceId: Int) {
    object Map : Screen("map", R.string.map)
    object Volunteers : Screen("volunteers", R.string.volunteers)
    object Community : Screen("community", R.string.community)
    object Profile : Screen("profile", R.string.profile)
}