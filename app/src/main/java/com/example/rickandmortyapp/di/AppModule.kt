package com.example.rickandmortyapp.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.example.rickandmortyapp.data.local.CharacterEntity
import com.example.rickandmortyapp.data.local.RickAndMortyDatabase
import com.example.rickandmortyapp.data.remote.RickAndMortyApi
import com.example.rickandmortyapp.data.remote.RickAndMortyRemoteMediator
import com.example.rickandmortyapp.util.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRickAndMortyDatabase(@ApplicationContext context: Context): RickAndMortyDatabase {
        return Room.databaseBuilder(
            context,
            RickAndMortyDatabase::class.java,
            "rick_and_morty_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRickAndMortyApi(): RickAndMortyApi {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RickAndMortyApi::class.java)
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCharacterPager(
        db: RickAndMortyDatabase,
        api: RickAndMortyApi
    ): Pager<Int, CharacterEntity> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            remoteMediator = RickAndMortyRemoteMediator(
                db = db,
                api = api
            ),
            pagingSourceFactory = { db.dao.pagingSource() }
        )
    }
}