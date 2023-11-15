package com.example.rickandmortyapp.data.remote

data class RickAndMortyResponse(
    val info: Info,
    val results: List<CharacterDto>
)