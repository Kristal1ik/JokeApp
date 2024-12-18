package com.kristallik.jokeapp.ui.add_joke

interface AddJokeView {
    fun showCategories(categories: List<String>)
    fun showError(errorMessage: String)
    fun saveJoke(message: String)
}