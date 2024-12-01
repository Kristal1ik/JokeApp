package com.kristallik.jokeapp.ui.add_joke

class AddJokePresenter(private val view: AddJokeView) {
    private val categories = listOf("Pun", "Satire", "Observational comedy")
    fun loadCategories() {
        view.showCategories(categories)
    }

    fun onSaveButtonClicked(category: String, question: String, answer: String) {
        if (question.isNotEmpty() && answer.isNotEmpty() && category.isNotEmpty()) {
            view.saveJoke("Your joke is added!")
        } else {
            view.showError("The form is filled out incorrectly!")
        }
    }
}