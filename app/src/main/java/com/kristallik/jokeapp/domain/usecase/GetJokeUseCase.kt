package com.kristallik.jokeapp.domain.usecase

import com.kristallik.jokeapp.data.repository.JokeRepository
import com.kristallik.jokeapp.domain.model.Joke

class GetJokesUseCase(private val jokeRepository: JokeRepository) {
    suspend fun execute(): List<Joke> {
        return jokeRepository.getAllSavedJokes()
    }
}