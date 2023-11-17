package com.example.rickandmortyapp.presentation.components

import android.util.Log
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.rickandmortyapp.R
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.model.Character
import com.example.rickandmortyapp.navigation.RickAndMortyScreens
import com.example.rickandmortyapp.presentation.search.SearchViewModel
import com.example.rickandmortyapp.util.toCharacter

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

//SearchScreen
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier, onSearch: (String) -> Unit = {}
) {
    Column(modifier = modifier) {
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

@Composable
fun InputField(
    modifier: Modifier = Modifier,
    valueState: MutableState<String>,
    onAction: KeyboardActions = KeyboardActions.Default
) {
    OutlinedTextField(
        value = valueState.value,
        onValueChange = {
            valueState.value = it
            Log.d("SearchScreen", "InputField: $it")
        },
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyBottomSheet(
    isSheetOpen: Boolean, viewModel: SearchViewModel,
    openBottomSheet: () -> Unit = {},
    closeBottomSheet: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    if (isSheetOpen) {
        ModalBottomSheet(
            onDismissRequest = {
                openBottomSheet()
                closeBottomSheet()
            },
            modifier = Modifier,
            sheetState = sheetState,
            dragHandle = {}
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FilterContent(viewModel = viewModel, closeBottomSheet = closeBottomSheet)
            }
        }
    }
}

@Composable
fun FilterContent(viewModel: SearchViewModel, closeBottomSheet: () -> Unit) {
    var filterGender by remember { mutableStateOf("") }
    var filterStatus by remember { mutableStateOf("") }
    Surface(modifier = Modifier.fillMaxWidth(), color = Color.White) {
        Column {
            val optionsGender = listOf("Male", "Female", "Unknown")
            val optionsStatus = listOf("Alive", "Dead", "Unknown")
            Text(
                text = "Filter options",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(20.dp)
            )
            MyDropDownMenu(options = optionsGender, label = "Gender", onTextChanged = {
                filterGender = it
            })
            MyDropDownMenu(options = optionsStatus, label = "Status", onTextChanged = {
                filterStatus = it
            })
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    viewModel.getCharactersFiltered(
                        gender = filterGender,
                        status = filterStatus
                    )
                    closeBottomSheet()
                },
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(bottom = 60.dp, end = 16.dp),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Text(text = "FILTER")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenu(options: List<String>, label: String, onTextChanged: (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 16.dp)
    ) {
        TextField(
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
                .alpha(0.7f),
            value = selectedOption,
            readOnly = true,
            onValueChange = { },
            label = { Text(text = label) },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                focusedLabelColor = MaterialTheme.colorScheme.primary,
                focusedTextColor = MaterialTheme.colorScheme.primary,
                focusedTrailingIconColor = MaterialTheme.colorScheme.primary
            ),
            shape = RectangleShape
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth(0.5f)
        ) {
            options.forEach { selected ->
                MyDropDownMenuItem(
                    itemText = selected,
                    onItemSelected = {
                        selectedOption = it
                        onTextChanged(it)
                        expanded = false
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyDropDownMenuItem(
    itemText: String,
    onItemSelected: (String) -> Unit
) {
    DropdownMenuItem(
        text = { Text(itemText) },
        onClick = {
            onItemSelected(itemText)
        },
        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
        modifier = Modifier.height(50.dp)
    )
}
