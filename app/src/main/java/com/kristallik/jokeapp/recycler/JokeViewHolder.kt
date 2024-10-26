package com.kristallik.jokeapp.recycler

import androidx.recyclerview.widget.RecyclerView
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.JokeItemBinding

// Cлужит своеобразным контейнером для всех компонентов, которые входят в элемент списка. Держит в памяти ссылку на конкретный элемент списка (view)
// Переиспользует для новых значений
class JokeViewHolder(
    private val binding: JokeItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(joke: Joke) {
        binding.category.text = joke.category
        binding.answer.text = joke.answer
        binding.question.text = joke.question
    }
}