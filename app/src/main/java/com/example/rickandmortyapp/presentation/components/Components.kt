package com.example.rickandmortyapp.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.navigation.RickAndMortyScreens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTopAppBar(
    title: String,
    isHomeScreen: Boolean = true,
    navController: NavController,
    onFilterIconClick: () -> Unit = {}
) {
    val rickAndMortyFamily = FontFamily(
        Font(R.font.get_schwifty)
    )
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                fontFamily = rickAndMortyFamily,
                color = Color.Green,
                style = MaterialTheme.typography.headlineMedium
            )
        },
        navigationIcon = {
            if (!isHomeScreen) {
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            }
        },
        actions = {
            if (!isHomeScreen) {
                IconButton(onClick = { onFilterIconClick()}) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            } else {
                Box {}
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}

@Composable
fun MyFAB(navController: NavController) {
    LargeFloatingActionButton(
        onClick = { navController.navigate(RickAndMortyScreens.SearchScreen.name) },
        contentColor = Color.White,
        containerColor = Color.Green,
    ) {
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            modifier = Modifier.size(30.dp)
        )
    }
}

@Composable
fun CharacterItem(
    character: Character,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    Card(
        modifier = modifier
            .fillMaxWidth(0.8f)
            .padding(15.dp)
            .clickable {
                navController.navigate(RickAndMortyScreens.DetailsScreen.name + "/${character.id}")
            },
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp, pressedElevation = 20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary),
    ) {
        val rickAndMortyFamily = FontFamily(
            Font(R.font.get_schwifty)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            Surface(
                shape = CircleShape,
                modifier = Modifier.padding(6.dp),
                border = BorderStroke(
                    width = 2.dp,
                    brush = Brush.linearGradient(
                        listOf(
                            Color.Green,
                            Color.White
                        )
                    )
                ),
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp)
                )
            }
            Text(
                text = character.name,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Green,
                fontFamily = rickAndMortyFamily,
                modifier = Modifier
                    .padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
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
