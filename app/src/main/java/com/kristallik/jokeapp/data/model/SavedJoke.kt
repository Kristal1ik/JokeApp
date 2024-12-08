package com.kristallik.jokeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kristallik.jokeapp.domain.model.Source

@Entity(tableName = "jokes_saved")
data class SavedJoke(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val source: Source
)