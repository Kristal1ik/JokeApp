package com.kristallik.jokeapp.presentation.ui.add_joke

import android.content.Context
import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.data.source.local.JokeDatabase
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
            val newJSavedJoke = SavedJoke(0, category, answer, question)
            saveToDB(newJSavedJoke, context)
            view.saveJoke("Your joke is added!")
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