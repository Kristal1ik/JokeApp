package com.kristallik.jokeapp.presentation.ui.addJoke

import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.domain.usecase.AddJokeUseCase

class AddJokePresenter(private val view: AddJokeView, private val addJokeUseCase: AddJokeUseCase) {
    private val categories = listOf("Pun", "Satire", "Observational comedy")
    fun loadCategories() {
        view.showCategories(categories)
    }

    suspend fun onSaveButtonClicked(
        category: String,
        question: String,
        answer: String,
    ) {
        if (question.isNotEmpty() && answer.isNotEmpty() && category.isNotEmpty()) {
            val newJSavedJoke = SavedJoke(0, category, answer, question)
            addJokeUseCase.execute(newJSavedJoke)
            view.saveJoke("Your joke is added!")
        } else {
            view.showError("The form is filled out incorrectly!")
        }
    }
}