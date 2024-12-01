package com.kristallik.jokeapp.data


data class JokeListResponse(
    val error: Boolean,
    val amount: Int,
    val jokes: List<Joke>
)