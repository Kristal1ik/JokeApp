package com.kristallik.jokeapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kristallik.jokeapp.data.model.NetworkJoke


@Dao
interface NetworkJokeDao {
    @Query("SELECT * FROM jokes_network")
    suspend fun getAllJokesSaved(): List<NetworkJoke>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: NetworkJoke)

    @Query("SELECT * FROM jokes_network WHERE id = :jokeId")
    suspend fun getJokeById(jokeId: Int): NetworkJoke?
}