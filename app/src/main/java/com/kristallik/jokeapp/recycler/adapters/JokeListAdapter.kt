package com.kristallik.jokeapp.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.JokeItemBinding
import com.kristallik.jokeapp.recycler.JokeViewHolder
import com.kristallik.jokeapp.recycler.util.JokeItemCallback

class JokeListAdapter(private val clickListener: (Int) -> Unit):
    ListAdapter<Joke, JokeViewHolder>(JokeItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(currentList[position])
        holder.itemView.setOnClickListener{
            handleJokeClick(position)
        }
    }

    override fun getItemCount(): Int = currentList.size

    private fun handleJokeClick(position: Int) {
        if (position != RecyclerView.NO_POSITION) {
            currentList[position]?.let { joke ->
                clickListener(position)
            }
        }
    }


    }