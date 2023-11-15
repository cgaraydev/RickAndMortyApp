package com.example.rickandmortyapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface RickAndMortyDao {

    @Upsert
    suspend fun upsertAll(characters: List<CharacterEntity>)

    @Query("SELECT * FROM characters_table")
    fun pagingSource(): PagingSource<Int, CharacterEntity>

    @Query("DELETE FROM characters_table")
    suspend fun deleteAll()
}