package com.example.rickandmortyapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.RickAndMortyDatabase
import com.example.rickandmortyapp.util.toCharacterEntity
import kotlinx.coroutines.delay

@OptIn(ExperimentalPagingApi::class)
class RickAndMortyRemoteMediator(
    private val db: RickAndMortyDatabase,
    private val api: RickAndMortyApi
) : RemoteMediator<Int, CharacterEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                    }
                }
            }
            delay(1000L)
            val response = api.getCharacters(page = loadKey)
            val characters = response.results
            db.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    db.dao.deleteAll()
                }
                val characterEntities = characters.map {
                    it.toCharacterEntity()
                }
                db.dao.upsertAll(characterEntities)

            }
            MediatorResult.Success(endOfPaginationReached = characters.isEmpty())

        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }

}