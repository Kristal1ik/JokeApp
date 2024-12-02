package com.kristallik.jokeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.NetworkJoke

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes_saved")
    suspend fun getAllJokesSaved(): List<Joke>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * FROM jokes_saved WHERE id = :jokeId")
    suspend fun getJokeById(jokeId: Int): Joke?
}

@Dao
interface NetworkJokeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNetworkJoke(joke: NetworkJoke)

    @Query("SELECT * FROM jokes_network")
    suspend fun getAllNetworkJokes(): List<NetworkJoke>
}