package com.example.rickandmortyapp.domain.repositories

import android.util.Log
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.data.remote.RickAndMortyApi
import com.example.rickandmortyapp.data.remote.RickAndMortyResponse
import com.example.rickandmortyapp.util.Resource
import javax.inject.Inject

class RickAndMortyRepository @Inject constructor(
    private val api: RickAndMortyApi
) {
    suspend fun getCharacters(page: Int): Resource<RickAndMortyResponse> {
        val response = try {
            api.getCharacters(page)
        } catch (e: Exception) {
            return Resource.Error("Something went wrong")
        }
        return Resource.Success(response)
    }

    suspend fun getCharacterById(id: Int): Resource<CharacterDto> {
        val response = try {
            api.getCharactersById(id)

        } catch (e: Exception) {
            return Resource.Error("Something went wrong")
        }
        return Resource.Success(response)
    }

    suspend fun getFilteredCharacters(
        name: String? = null,
        status: String? = null,
        species: String? = null,
        type: String? = null,
        gender: String? = null
    ): Resource<RickAndMortyResponse> {
        val response = try {
            api.getFilteredCharacters(
                name = name,
                status = status,
                species = species,
                type = type,
                gender = gender
            )
        } catch (e: Exception) {
            Log.d("rickandmortyrepository", "getFilteredCharacters: ")
            return Resource.Error("Something went wrong")
        }
        return Resource.Success(response)
    }
}