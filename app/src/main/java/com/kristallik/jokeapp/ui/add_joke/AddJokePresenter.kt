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

    fun onSaveButtonClicked(category: String, question: String, answer: String, context:Context) {
        if (question.isNotEmpty() && answer.isNotEmpty() && category.isNotEmpty()) {
            val newSavedJoke = SavedJoke(0, category, question, answer, Source.TYPE_MANUAL)
            saveToDB(newSavedJoke, context)
            view.saveJoke("Your joke is added!")
        } else {
            view.showError("The form is filled out incorrectly!")
        }
    }

    private fun saveToDB(joke: SavedJoke, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            JokeDatabase.getDatabase(context).savedJokeDao().insertJoke(joke)
        }
    }
}