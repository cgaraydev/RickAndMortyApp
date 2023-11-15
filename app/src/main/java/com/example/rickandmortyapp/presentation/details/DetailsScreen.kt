package com.example.rickandmortyapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.presentation.components.MyTopAppBar
import com.example.rickandmortyapp.util.Resource

@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
fun CharacterDetails(character: CharacterDto) {
    val rickAndMortyFamily = FontFamily(
        Font(R.font.get_schwifty)
    )
    AsyncImage(
        model = character.image,
        contentDescription = null,
        modifier = Modifier.fillMaxWidth()
    )
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = character.name,
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black,
            fontFamily = rickAndMortyFamily,
            maxLines = 2,
            textAlign = TextAlign.Center
        )
        Divider(modifier = Modifier.padding(8.dp))
        Row (modifier = Modifier.fillMaxWidth()) {
            Text(text = "Origin: ")
            Text(text = character.origin.name)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row (verticalAlignment = Alignment.CenterVertically) {
            Row {
                Text(text = "Specie: ")
                Text(text = character.species)
            }
            Spacer(modifier = Modifier.weight(1f))
            when (character.gender) {
                "Female" -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_female),
                        contentDescription = null
                    )
                }

                "Male" -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_male),
                        contentDescription = null
                    )
                }

                else -> {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_unknown),
                        contentDescription = null
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Row(
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Location: ")
            Text(text = character.location.name)
        }

    }
}
