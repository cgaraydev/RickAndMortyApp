package com.example.rickandmortyapp.presentation.details

import androidx.lifecycle.ViewModel
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.repositories.RickAndMortyRepository
import com.example.rickandmortyapp.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val repository: RickAndMortyRepository
) : ViewModel() {

    suspend fun getCharacterById(id: Int): Resource<CharacterDto> {
        return repository.getCharacterById(id)
    }

}