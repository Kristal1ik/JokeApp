package com.kristallik.jokeapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.kristallik.jokeapp.data.model.SavedJoke


@Dao
interface SavedJokeDao {
    @Query("SELECT * FROM jokes_saved")
    suspend fun getAllJokesSaved(): List<SavedJoke>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertJoke(joke: SavedJoke)

    @Query("SELECT * FROM jokes_saved WHERE id = :jokeId")
    suspend fun getJokeById(jokeId: Int): SavedJoke?
}

