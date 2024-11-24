package com.kristallik.jokeapp.recycler

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.JokeItemBinding
import com.kristallik.jokeapp.databinding.JokeItemNetBinding

// Служит своеобразным контейнером для всех компонентов, которые входят в элемент списка. Держит в памяти ссылку на конкретный элемент списка (view)
// Переиспользует для новых значений
sealed class JokeViewHolder(binding: View) : RecyclerView.ViewHolder(binding) {
    abstract fun bind(joke: Joke)
    class NetworkJokeViewHolder(private val binding: JokeItemNetBinding) :
        JokeViewHolder(binding.root) {
        override fun bind(joke: Joke) {
            binding.question.text = joke.setup
            binding.answer.text = joke.delivery
        }
    }

    class ManualJokeViewHolder(private val binding: JokeItemBinding) :
        JokeViewHolder(binding.root) {
        override fun bind(joke: Joke) {
            binding.question.text = joke.setup
            binding.answer.text = joke.delivery
        }
    }
}