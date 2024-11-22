package com.kristallik.jokeapp.ui.add_joke

class AddJokePresenter(private val view: AddJokeView) {
    private val categories = listOf("Category", "Pun", "Satire", "Observational comedy")

    fun loadCategories() {
        view.showCategories(categories)
    }

    fun onSaveButtonClicked(category: String, question: String, answer: String) {
        if (category != "Category" && question.isNotEmpty() && answer.isNotEmpty()) {
            view.saveJoke("Your joke is added!")
        } else {
            view.showError("The form is filled out incorrectly!")
        }
    }
}