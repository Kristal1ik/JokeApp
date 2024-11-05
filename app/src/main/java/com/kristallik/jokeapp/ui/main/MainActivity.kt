package com.kristallik.jokeapp.ui.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.ActivityMainBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsActivity

class MainActivity : AppCompatActivity(), MainView {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainPresenter
    private val adapter = JokeListAdapter {
        startActivity(JokeDetailsActivity.getInstance(this, it))
    }
    private var currentPosition: Int = 0

    companion object {
        const val CONST_CURRENT_POSITION = "CURRENT_POSITION"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = MainPresenter(this)
        createRecyclerViewList()

        savedInstanceState?.let {
            currentPosition = it.getInt(CONST_CURRENT_POSITION, 0)
        }

        presenter.loadJokes()
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

    override fun showJokes(jokes: ArrayList<Joke>) {
        adapter.submitList(jokes)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
    }
}