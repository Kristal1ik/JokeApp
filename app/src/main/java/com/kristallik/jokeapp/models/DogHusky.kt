package com.kristallik.jokeapp.models

import com.kristallik.jokeapp.enums.BiteTypes
import com.kristallik.jokeapp.interfaces.Dog

class DogHusky(override val weight: Double,
            override val age: Int,
            override val biteType: BiteTypes = BiteTypes.STRAIGHT):
    Dog {
}
