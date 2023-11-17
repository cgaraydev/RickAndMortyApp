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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
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
            SearchComponents(modifier = Modifier.fillMaxWidth()) {
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

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchComponents(
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
