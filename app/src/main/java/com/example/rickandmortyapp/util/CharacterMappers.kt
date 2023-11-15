package com.example.rickandmortyapp.util

import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.remote.CharacterDto
import com.example.rickandmortyapp.domain.model.Character

fun CharacterDto.toCharacterEntity(): CharacterEntity {
    return CharacterEntity(
        created = created,
        gender = gender,
        id = id,
        image = image,
        name = name,
        species = species,
        status = status,
        type = type,
        url = url
    )
}

fun CharacterEntity.toCharacter(): Character {
    return Character(
        created = created,
        gender = gender,
        id = id,
        image = image,
        name = name,
        species = species,
        status = status,
        type = type,
        url = url
    )
}

fun CharacterDto.toCharacter(): Character {
    return Character(
        created = created,
        gender = gender,
        id = id,
        image = image,
        name = name,
        species = species,
        status = status,
        type = type,
        url = url
    )
}