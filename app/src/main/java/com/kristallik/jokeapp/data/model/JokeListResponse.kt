package com.kristallik.jokeapp.data.model

import com.kristallik.jokeapp.domain.model.Joke


data class JokeListResponse(
    val error: Boolean,
    val amount: Int,
    val jokes: List<Joke>
)