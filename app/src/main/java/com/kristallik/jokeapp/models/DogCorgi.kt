package com.kristallik.jokeapp.models

import com.kristallik.jokeapp.enums.BiteTypes
import com.kristallik.jokeapp.interfaces.Dog

class DogCorgi(
    override val weight: Double,
    override val age: Int
) :
    Dog {
    override val biteType: BiteTypes = BiteTypes.UNDERBITE
}