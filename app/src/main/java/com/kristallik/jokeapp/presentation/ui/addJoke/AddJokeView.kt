package com.kristallik.jokeapp.presentation.ui.addJoke

interface AddJokeView {
    fun showCategories(categories: List<String>)
    fun showError(errorMessage: String)
    fun saveJoke(message: String)
}