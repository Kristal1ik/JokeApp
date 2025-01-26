package com.kristallik.jokeapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "jokes_network")
data class NetworkJoke(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val category: String,
    val setup: String,
    val delivery: String,
    val lastUpdated: Long = System.currentTimeMillis()
)
