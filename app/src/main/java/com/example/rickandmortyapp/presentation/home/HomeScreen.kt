package com.example.rickandmortyapp.presentation.home

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.rickandmortyapp.presentation.components.CharacterItem
import com.example.rickandmortyapp.presentation.components.MyFAB
import com.example.rickandmortyapp.presentation.components.MyTopAppBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Rick and Morty",
                navController = navController
            )
        },
        floatingActionButton = {
            MyFAB(navController)
        }
    ) {
        val characters = viewModel.characterPagingFlow.collectAsLazyPagingItems()
        val context = LocalContext.current
        LaunchedEffect(key1 = characters.loadState) {
            if (characters.loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    context,
                    "Error: " + (characters.loadState.refresh as LoadState.Error).error.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            if (characters.loadState.refresh is LoadState.Loading) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.SpaceAround,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(characters) { character ->
                        if (character != null) {
                            CharacterItem(character = character, navController = navController)
                        }
                    }
                    item {
                        if (characters.loadState.append is LoadState.Loading) {
                            CircularProgressIndicator(
                                modifier = Modifier.align(Alignment.Center),
                                color = Color.Green
                            )
                        }
                    }
                }
            }
        }
    }
}


