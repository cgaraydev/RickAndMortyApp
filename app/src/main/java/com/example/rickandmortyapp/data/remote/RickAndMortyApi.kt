package com.example.rickandmortyapp.data.remote

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyApi {

    @GET("character")
    suspend fun getCharacters(
        @Query("page") page: Int
    ): RickAndMortyResponse

    @GET("character/{id}")
    suspend fun getCharactersById(
        @Path("id") id: Int
    ): CharacterDto

    @GET("character")
    suspend fun getFilteredCharacters(
        @Query("name") name: String? = null,
        @Query("status") status: String? = null,
        @Query("species") species: String? = null,
        @Query("type") type: String? = null,
        @Query("gender") gender: String? = null
    ): RickAndMortyResponse
}

