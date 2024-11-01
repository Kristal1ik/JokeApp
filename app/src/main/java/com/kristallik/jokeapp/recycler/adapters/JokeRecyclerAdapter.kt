package com.kristallik.jokeapp.recycler.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.JokeItemBinding
import com.kristallik.jokeapp.recycler.JokeViewHolder
import com.kristallik.jokeapp.recycler.util.JokeDiffUtilCallback

class JokeRecyclerAdapter : RecyclerView.Adapter<JokeViewHolder>() {

    private var jokesList: ArrayList<Joke> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JokeViewHolder {
        val binding = JokeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JokeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: JokeViewHolder, position: Int) {
        holder.bind(jokesList[position])
    }

    override fun getItemCount(): Int = jokesList.size

    @SuppressLint("NotifyDataSetChanged")
    fun setNewData(newData: ArrayList<Joke>) {
        val diffUtilCallback = JokeDiffUtilCallback(jokesList, newData)
        val calculateDiff = DiffUtil.calculateDiff(diffUtilCallback)
        jokesList = newData
        println("adapter")
        calculateDiff.dispatchUpdatesTo(this)
    }
}