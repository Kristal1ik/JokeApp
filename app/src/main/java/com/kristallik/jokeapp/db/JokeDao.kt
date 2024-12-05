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
    suspend fun insertJoke(joke: SavedJoke)

    @Query("SELECT * FROM jokes_saved WHERE id = :jokeId")
    suspend fun getJokeById(jokeId: Int): SavedJoke?
}

@Dao
interface NetworkJokeDao {
    @Query("SELECT * FROM jokes_network")
    suspend fun getAllJokesSaved(): List<NetworkJoke>

    @Insert
    suspend fun insertJoke(joke: NetworkJoke)

    @Query("SELECT * FROM jokes_network WHERE id = :jokeId")
    suspend fun getJokeById(jokeId: Int): NetworkJoke?
}