package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.rickandmortyapp.presentation.home.HomeScreen
import com.example.rickandmortyapp.presentation.splash.SplashScreen

@Composable
fun RickAndMortyNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RickAndMortyScreens.SplashScreen.name) {
        composable(RickAndMortyScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(RickAndMortyScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }
    }
}