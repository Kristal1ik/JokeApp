package com.kristallik.jokeapp.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kristallik.jokeapp.data.model.NetworkJoke
import com.kristallik.jokeapp.data.model.SavedJoke


// Определение класса базы данных
@Database(entities = [SavedJoke::class, NetworkJoke::class], version = 4)
abstract class JokeDatabase : RoomDatabase() {
    abstract fun savedJokeDao(): SavedJokeDao
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
                ).fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}

