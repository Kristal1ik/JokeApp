package com.kristallik.jokeapp.presentation.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.kristallik.jokeapp.domain.model.Joke

class JokeItemCallback : DiffUtil.ItemCallback<Joke>() {
    override fun areItemsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Joke, newItem: Joke): Boolean {
        return oldItem == newItem
    }
}