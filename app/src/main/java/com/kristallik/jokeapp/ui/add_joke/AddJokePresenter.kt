package com.kristallik.jokeapp.ui.add_joke

import android.content.Context
import com.kristallik.jokeapp.data.Joke
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
            val newJoke = Joke(
                id = 0,
                category = category,
                setup = question,
                delivery = answer,
                source = Source.TYPE_MANUAL
            )
            saveJoke(newJoke, context)
        } else {
            view.showError("The form is filled out incorrectly!")
        }
    }

    private fun saveJoke(joke: Joke, context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            val jokeDao = JokeDatabase.getDatabase(context).jokeDao()
            jokeDao.insertJoke(joke)
        }
        view.saveJoke("Your joke is added!")

    }
}