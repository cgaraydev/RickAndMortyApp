package com.example.rickandmortyapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [CharacterEntity::class], version = 1)
abstract class RickAndMortyDatabase : RoomDatabase() {
    abstract val dao: RickAndMortyDao
}