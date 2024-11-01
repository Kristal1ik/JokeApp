package com.kristallik.jokeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.databinding.ActivityMainBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.recycler.util.JokeItemCallback

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val itemCallback = JokeItemCallback()
    private val adapter = JokeListAdapter(itemCallback)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createRecyclerViewList()
        val generator = JokeGenerator()
        binding.button.setOnClickListener {
            val data = generator.generateJokesData()
            adapter.submitList(data)
        }

    }

    private fun createRecyclerViewList() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(this)
    }
}