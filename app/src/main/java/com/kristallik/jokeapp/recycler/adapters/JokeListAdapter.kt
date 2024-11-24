package com.kristallik.jokeapp.recycler.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.JokeItemBinding
import com.kristallik.jokeapp.databinding.JokeItemNetBinding
import com.kristallik.jokeapp.recycler.JokeViewHolder
import com.kristallik.jokeapp.recycler.util.JokeItemCallback

class JokeListAdapter(private val clickListener: (Int) -> Unit) :
    ListAdapter<Joke, JokeViewHolder>(JokeItemCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        return if (viewType == 1) {
            val binding =
                JokeItemNetBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            JokeViewHolder.NetworkJokeViewHolder(binding)
        } else {
            val binding =
                JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            JokeViewHolder.ManualJokeViewHolder(binding)
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
        return if (getItem(position).source == "net") {
            1
        } else {
            0
        }
    }

    override fun getItemCount(): Int = currentList.size
}