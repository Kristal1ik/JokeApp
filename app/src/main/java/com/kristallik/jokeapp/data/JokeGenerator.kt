package com.kristallik.jokeapp.data

class JokeGenerator {
    private val questions = listOf(
        "What does Santa suffer from if he gets stuck in a chimney?",
        "Why does a computer never feel hungry?",
        "Why is the math book sad?",
        "What did one ocean say to the other?",
        "Which ocean is the smartest?",
        "What does a cat do when it wants to play with the computer?",
        "Why does the duck always win at cards?"
    )

    private val answers = listOf(
        "Claustrophobia!",
        "Because it always has plenty of bytes!",
        "Because it has too many problems!",
        "Nothing, they just waved!",
        "The Quiet one, because it always thinks 'silently'!",
        "It clicks on the 'mouse'!",
        "Because it always knows when to 'quack'! длинный ответ, чтобы убедиться, что он сокращается до многоточия"
    )

    fun generateJokesData(): ArrayList<Joke> {
        val jokes = ArrayList<Joke>()
        for (i in questions.indices) {
            jokes.add(i, Joke("Pun", questions[i], answers[i]))
        }
        return jokes
    }
}