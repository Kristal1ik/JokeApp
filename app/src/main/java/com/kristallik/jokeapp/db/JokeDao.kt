package com.kristallik.jokeapp.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.kristallik.jokeapp.data.Joke

@Dao
interface JokeDao {
    @Query("SELECT * FROM jokes_saved")
    suspend fun getAllJokesSaved(): List<Joke>

    @Insert
    suspend fun insertJoke(joke: Joke)

    @Query("SELECT * FROM jokes_saved WHERE id = :jokeId")
    suspend fun getJokeById(jokeId: Int): Joke?
}
