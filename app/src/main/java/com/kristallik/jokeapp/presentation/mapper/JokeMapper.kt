package com.kristallik.jokeapp.presentation.mapper
import com.kristallik.jokeapp.data.model.SavedJoke
import com.kristallik.jokeapp.domain.model.Joke
import com.kristallik.jokeapp.domain.model.Source
class JokeMapper {
    fun mapSavedToJoke(savedJoke: SavedJoke): Joke {
        return Joke(
            id = savedJoke.id,
            category = savedJoke.category,
            setup = savedJoke.setup,
            delivery = savedJoke.delivery,
            source = Source.TYPE_MANUAL
        )
    }
}