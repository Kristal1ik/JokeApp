package com.kristallik.jokeapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "jokes_saved")
data class Joke(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val setup: String,
    val delivery: String,
    val source: Source,
    val lastUpdated: Long = System.currentTimeMillis()


) : Serializable

@Entity(tableName = "jokes_network")
data class NetworkJoke(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val category: String,
    val setup: String,
    val delivery: String,
    val source: Source,
    val lastUpdated: Long = System.currentTimeMillis()
) : Serializable

enum class Source {
    TYPE_NETWORK,
    TYPE_MANUAL
}