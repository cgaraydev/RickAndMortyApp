package com.example.rickandmortyapp.presentation.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.presentation.components.CharactersList
import com.example.rickandmortyapp.presentation.components.MyBottomSheet
import com.example.rickandmortyapp.presentation.components.MyTopAppBar
import com.example.rickandmortyapp.presentation.components.SearchBar

@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    var isSheetOpen by remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Search",
                navController = navController,
                isHomeScreen = false
            ) {
                isSheetOpen = true
            }
        },
        bottomBar = {
            MyBottomSheet(isSheetOpen = isSheetOpen, viewModel = viewModel) {
                isSheetOpen = false
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val rickAndMortyFamily = FontFamily(
                Font(R.font.get_schwifty)
            )
            SearchBar(modifier = Modifier.fillMaxWidth()) {
                viewModel.getCharactersFiltered(name = it)
            }
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Discover!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Green,
                fontFamily = rickAndMortyFamily,
                textAlign = TextAlign.Center
            )
            CharactersList(navController = navController, viewModel = viewModel)
        }
    }
}








