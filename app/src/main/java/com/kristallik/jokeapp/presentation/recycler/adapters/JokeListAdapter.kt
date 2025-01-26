package com.kristallik.jokeapp.presentation.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kristallik.jokeapp.domain.model.Joke
import com.kristallik.jokeapp.domain.model.Source
import com.kristallik.jokeapp.databinding.JokeItemBinding
import com.kristallik.jokeapp.databinding.JokeItemNetBinding
import com.kristallik.jokeapp.presentation.recycler.viewholder.JokeViewHolder
import com.kristallik.jokeapp.presentation.recycler.util.JokeItemCallback

class JokeListAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Joke, JokeViewHolder>(JokeItemCallback()) {
    companion object {
        const val TYPE_NETWORK = 1
        const val TYPE_MANUAL = 2
        const val TYPE_OTHER = 3
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return when (viewType) {
            TYPE_NETWORK -> {
                val binding =
                    JokeItemNetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                JokeViewHolder.NetworkJokeViewHolder(binding)
            }

            TYPE_MANUAL -> {
                val binding =
                    JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                JokeViewHolder.ManualJokeViewHolder(binding)
            }

            else -> {
                throw IllegalStateException("Invalid view type!")

            }
        }
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        val joke = getItem(position)
        holder.bind(joke)
        holder.itemView.setOnClickListener {
            clickListener(position)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position).source) {
            Source.TYPE_NETWORK -> TYPE_NETWORK
            Source.TYPE_MANUAL -> TYPE_MANUAL
            else -> TYPE_OTHER
        }
    }

    override fun getItemCount(): Int = currentList.size
}