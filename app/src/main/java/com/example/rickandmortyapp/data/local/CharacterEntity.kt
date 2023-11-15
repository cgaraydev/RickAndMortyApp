package com.example.rickandmortyapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "characters_table")
data class CharacterEntity(
    val created: String,
//    val episode: List<String>,
    val gender: String,
    @PrimaryKey
    val id: Int,
    val image: String,
//    val location: Location,
    val name: String,
//    val origin: Origin,
    val species: String,
    val status: String,
    val type: String,
    val url: String
)