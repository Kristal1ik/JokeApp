package com.kristallik.jokeapp.models

import com.kristallik.jokeapp.enums.BehaviorTypes
import com.kristallik.jokeapp.interfaces.Cat

class CatScottish(override val weight: Double, override val age: Int) : Cat {
    override val behaviorType: BehaviorTypes = BehaviorTypes.PASSIVE
}