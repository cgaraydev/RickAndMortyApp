package com.example.rickandmortyapp.presentation.details

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.presentation.components.CharacterDetails
import com.example.rickandmortyapp.presentation.components.MyTopAppBar
import com.example.rickandmortyapp.util.Resource

@Composable
fun DetailsScreen(
    navController: NavController,
    characterId: Int,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    Scaffold(topBar = {
        MyTopAppBar(title = "Details", navController = navController, isHomeScreen = false) {
            navController.popBackStack()
        }
    }) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            val characterDetails =
                produceState<Resource<CharacterDto>>(initialValue = Resource.Loading()) {
                    value = viewModel.getCharacterById(characterId)
                }.value
            if (characterDetails.data == null) {
                CircularProgressIndicator()
            } else {
                CharacterDetails(character = characterDetails.data)
            }
        }
    }
}


