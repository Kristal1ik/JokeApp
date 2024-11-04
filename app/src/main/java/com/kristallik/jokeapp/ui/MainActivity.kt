package com.kristallik.jokeapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.databinding.ActivityMainBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = JokeListAdapter {
        startActivity(JokeDetailsActivity.getInstance(this, it))
    }

    // Переменная для хранения текущей позиции в списке
    private var currentPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        createRecyclerViewList()

        savedInstanceState?.let {
            currentPosition = it.getInt("CURRENT_POSITION", 0)
        }

        val generator = JokeGenerator
        val data = generator.generateJokesData()
        adapter.submitList(data)
        binding.recyclerview.scrollToPosition(currentPosition)
    }

    private fun createRecyclerViewList() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        currentPosition =
            (binding.recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
        outState.putInt("CURRENT_POSITION", currentPosition)
    }
}