package com.kristallik.jokeapp.recycler.util

import androidx.recyclerview.widget.DiffUtil
import com.kristallik.jokeapp.data.Joke

class JokeDiffUtilCallback(
    private val oldList: ArrayList<Joke>,
    private val newList: ArrayList<Joke>
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    // Тот же самый элемент?
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldList[oldItemPosition].id == newList[newItemPosition].id)
    }
    // Контент этого элемента отличвется?
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        println(oldList[oldItemPosition])
        println(newList[newItemPosition])
        println("du")
        return (oldList[oldItemPosition] == newList[newItemPosition])
    }
}