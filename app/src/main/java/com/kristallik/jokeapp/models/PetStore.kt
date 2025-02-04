package com.kristallik.jokeapp.models

import com.kristallik.jokeapp.interfaces.Animal
import com.kristallik.jokeapp.interfaces.Cat
import com.kristallik.jokeapp.interfaces.Dog

class PetStore {
    fun identifyAnimal(animal: Animal?): String? {
        return when (animal) {
            is Dog -> "It's a DOG\n\"U・ᴥ・U\""
            is Cat -> "It's a CAT\n\"/ᐠ.｡.ᐟ\\\\ᵐᵉᵒʷˎˊ˗\""
            else -> null
        }
    }

    fun createAnimal(animalInput: String): Animal? {
        return when (animalInput.lowercase()) {
            "husky" -> DogHusky(30.0, 5)
            "corgi" -> DogCorgi(10.0, 2)
            "scottish" -> CatScottish(5.5, 2)
            "siamese" -> CatSiamese(3.5, 1)
            else -> null
        }
    }
}
