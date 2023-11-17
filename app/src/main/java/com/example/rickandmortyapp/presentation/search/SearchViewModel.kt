package com.example.rickandmortyapp.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.repositories.RickAndMortyRepository
import com.example.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    var charactersList: List<CharacterDto> by mutableStateOf(listOf())
    private var isLoading: Boolean by mutableStateOf(false)
    private val page = Random.nextInt(1, 42)


    init {
        getRandomPage()
    }

    private fun getRandomPage() {
        viewModelScope.launch {
            val response = repository.getCharacters(page)
            try {
                when (response) {
                    is Resource.Success -> {
                        charactersList = response.data!!.results
                        if (charactersList.isNotEmpty()) isLoading = false
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Log.d("SearchViewModel", "getRandomPage: Error")
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                isLoading = false
                Log.d("SearchViewModel", "getRandomPage: ${e.message.toString()}")
            }
        }
    }

    fun getCharactersFiltered(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ) {
        viewModelScope.launch {
            isLoading = true
            val response = repository.getFilteredCharacters(name, status, species, type, gender)
            isLoading = false

            try {
                when (response) {

                    is Resource.Success -> {
                        isLoading = false
                        charactersList = response.data!!.results
                    }

                    is Resource.Error -> {
                        isLoading = false
                        Log.d("SearchViewModel", "getCharactersFiltered: Error ${response.message}")
                    }

                    else -> {}
                }
            } catch (e: Exception) {
                Log.d("SearchViewModel", "getCharactersFiltered: ${e.message.toString()}")
            }
        }
    }
}