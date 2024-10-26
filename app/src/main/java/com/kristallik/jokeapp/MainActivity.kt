package com.kristallik.jokeapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.databinding.ActivityMainBinding
import com.kristallik.jokeapp.recycler.JokeRecyclerAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val adapter = JokeRecyclerAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.recyclerview.adapter = adapter
        val generator = JokeGenerator()
        val data = generator.generateJokesData()
        adapter.setNewData(data)
    }
}