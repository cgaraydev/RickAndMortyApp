package com.example.rickandmortyapp.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.rickandmortyapp.presentation.details.DetailsScreen
import com.example.rickandmortyapp.presentation.details.DetailsViewModel
import com.example.rickandmortyapp.presentation.home.HomeScreen
import com.example.rickandmortyapp.presentation.search.SearchScreen
import com.example.rickandmortyapp.presentation.search.SearchViewModel
import com.example.rickandmortyapp.presentation.splash.SplashScreen

@Composable
fun RickAndMortyNavigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = RickAndMortyScreens.HomeScreen.name
    ) {
        composable(RickAndMortyScreens.SplashScreen.name) {
            SplashScreen(navController)
        }
        composable(RickAndMortyScreens.HomeScreen.name) {
            HomeScreen(navController = navController)
        }

        val detailName = RickAndMortyScreens.DetailsScreen.name
        composable(
            route = "$detailName/{characterId}",
            arguments = listOf(navArgument("characterId") {
                type = NavType.IntType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getInt("characterId").let {
                val viewModel = hiltViewModel<DetailsViewModel>()
                DetailsScreen(navController, it!!.toInt(), viewModel)
            }
        }

        composable(RickAndMortyScreens.SearchScreen.name) {
            val viewModel = hiltViewModel<SearchViewModel>()
            SearchScreen(navController = navController, viewModel = viewModel)
        }
    }
}