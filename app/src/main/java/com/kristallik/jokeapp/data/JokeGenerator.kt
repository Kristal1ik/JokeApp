package com.kristallik.jokeapp.data


// object, чтобы не создавался при каждом вызове новый экземпляр
object JokeGenerator {
    val jokes = ArrayList<Joke>()
}