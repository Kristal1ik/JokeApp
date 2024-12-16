package com.kristallik.jokeapp.ui.add_joke

import android.content.Context
import com.kristallik.jokeapp.data.SavedJoke
import com.kristallik.jokeapp.data.Source
import com.kristallik.jokeapp.db.JokeDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AddJokePresenter(private val view: AddJokeView) {
    private val categories = listOf("Pun", "Satire", "Observational comedy")
    fun loadCategories() {
        view.showCategories(categories)
    }

    fun onSaveButtonClicked(category: String, question: String, answer: String, context: Context) {
        if (question.isNotEmpty() && answer.isNotEmpty() && category.isNotEmpty()) {
            val newJSavedJoke = SavedJoke(0, category, answer, question, Source.TYPE_MANUAL)
            saveToDB(newJSavedJoke, context)
            view.saveJoke("Your joke is added!") // save to the generator.jokes
        } else {
            view.showError("The form is filled out incorrectly!")
        }
    }

    private fun saveToDB(joke: SavedJoke, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val savedJokeDao = JokeDatabase.getDatabase(context).savedJokeDao()
            savedJokeDao.insertJoke(joke)
        }
    }

}