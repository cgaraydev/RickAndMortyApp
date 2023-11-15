package com.example.rickandmortyapp.presentation.search

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.model.Character
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
    var isLoading: Boolean by mutableStateOf(false)


    init {
        getRandomPage()
    }

    private fun getRandomPage() {
        viewModelScope.launch {
            val page = Random.nextInt(1, 42)
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
}