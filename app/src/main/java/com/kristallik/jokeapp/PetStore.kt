package com.kristallik.jokeapp
import com.kristallik.jokeapp.interfaces.Animal
import com.kristallik.jokeapp.interfaces.Dog
import com.kristallik.jokeapp.models.CatScottish
import com.kristallik.jokeapp.models.CatSiamese
import com.kristallik.jokeapp.models.DogCorgi
import com.kristallik.jokeapp.models.DogHusky

class PetStore {
    fun identifyAnimal(animal: String) : Animal? {
        return when (animal.lowercase()){
            "husky" -> DogHusky(30.0, 5)
            "corgi" -> DogCorgi(10.0, 2)
            "scottish" -> CatScottish(5.5, 2)
            "siamese" -> CatSiamese(3.5, 1)
            else -> null
        }
    }
}
fun main() {
    print("Enter the name of the breed (husky, corgi, scottish, siamese): ")
    val animalInput = readlnOrNull()
    val petStore = PetStore()
    val animal = animalInput?.let { petStore.identifyAnimal(it) }
    if (animal != null) {
        println("It's a ${if (animal is Dog) "DOG" else "CAT"} weighting ${animal.weight} and aged ${animal.age}.")
        println(if (animal is Dog) "U・ᴥ・U" else "/ᐠ.｡.ᐟ\\ᵐᵉᵒʷˎˊ˗")
    } else {
        println("The unknown breed!")
    }

}