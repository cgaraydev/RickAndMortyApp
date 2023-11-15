package com.example.rickandmortyapp.presentation.search

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.presentation.components.CharacterItem
import com.example.rickandmortyapp.presentation.components.MyTopAppBar
import com.example.rickandmortyapp.util.toCharacter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavController, viewModel: SearchViewModel = hiltViewModel()) {
    Scaffold(topBar = {
        MyTopAppBar(title = "Search", navController = navController, isHomeScreen = false) {
            navController.popBackStack()
        }
    }) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val rickAndMortyFamily = FontFamily(
                Font(R.font.get_schwifty)
            )
            SearchComponents(modifier = Modifier.fillMaxWidth()) {
                Log.d("SearchScreen", "SearchScreen: $it")
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchComponents(
    modifier: Modifier = Modifier, onSearch: (String) -> Unit = {}
) {
    Column (modifier = modifier) {
        val searchState = remember { mutableStateOf("") }
        val keyboardController = LocalSoftwareKeyboardController.current
        val valid = remember(searchState.value) { searchState.value.isNotEmpty() }
        InputField(valueState = searchState, onAction = KeyboardActions {
            if (!valid) return@KeyboardActions
            onSearch(searchState.value)
            searchState.value = ""
            keyboardController?.hide()
        })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = { valueState.value = it },
        label = { Text(text = "Search character") },
        singleLine = true,
        textStyle = TextStyle(fontSize = 18.sp, color = Color.Green),
        modifier = modifier
            .padding(10.dp)
            .fillMaxWidth(),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = onAction
    )
}

@Composable
fun CharactersList(navController: NavController, viewModel: SearchViewModel) {
    LazyVerticalGrid(columns = GridCells.Fixed(2), modifier = Modifier.fillMaxWidth()) {
        items(viewModel.charactersList) {
            val newCharacter = it.toCharacter()
            CharacterItem(character = newCharacter, navController = navController)
        }
    }
}