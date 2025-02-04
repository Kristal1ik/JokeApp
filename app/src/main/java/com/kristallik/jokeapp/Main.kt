package com.kristallik.jokeapp

import com.kristallik.jokeapp.models.PetStore


fun main() {
    print("Enter the name of the breed (husky, corgi, scottish, siamese): ")
    val petStore = PetStore()
    val animalInput = readlnOrNull()
    if (animalInput != null) {
        val animal = petStore.createAnimal(animalInput)
        if (animal != null) {
            val breed = petStore.identifyAnimal(animal)
            println(breed)
        } else {
            println("The unknown breed!")
        }
    }
}
