package com.kristallik.jokeapp.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.NetworkJoke

@Database(entities = [Joke::class, NetworkJoke::class], version = 4, exportSchema = false)
abstract class JokeDatabase : RoomDatabase() {

    abstract fun jokeDao(): JokeDao
    abstract fun networkJokeDao(): NetworkJokeDao

    companion object {
        @Volatile
        private var INSTANCE: JokeDatabase? = null

        fun getDatabase(context: Context): JokeDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    JokeDatabase::class.java,
                    "joke_database"
                ).fallbackToDestructiveMigration().build()
                INSTANCE = instance
                instance
            }
        }
    }
}