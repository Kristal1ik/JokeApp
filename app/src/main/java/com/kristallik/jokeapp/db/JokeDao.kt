package com.kristallik.jokeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kristallik.jokeapp.data.NetworkJoke
import com.kristallik.jokeapp.data.SavedJoke

@Dao
interface SavedJokeDao {
    @Query("SELECT * FROM jokes_saved")
    suspend fun getAllJokesSaved(): List<SavedJoke>

    @Insert
    fun insertJoke(savedJoke: SavedJoke)
}

@Dao
interface NetworkJokeDao {
    @Query("SELECT * FROM jokes_network")
    suspend fun getAllJokesNetwork(): List<NetworkJoke>

    @Insert
    suspend fun insertJoke(networkJoke: NetworkJoke)
}