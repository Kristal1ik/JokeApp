package com.kristallik.jokeapp.interfaces
import com.kristallik.jokeapp.enums.BiteTypes

interface Dog: Animal {
    val biteType: BiteTypes
}