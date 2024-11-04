package com.kristallik.jokeapp.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator
import com.kristallik.jokeapp.databinding.ActivityJokeDetailsBinding

class JokeDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJokeDetailsBinding
    private val generator = JokeGenerator
    private var position: Int = -1

    companion object {
        private const val CONST_JOKE_POSITION_EXTRA = "JOKE_POSITION"
        fun getInstance(context: Context, position: Int): Intent {
            return Intent(context, JokeDetailsActivity::class.java).apply {
                putExtra(CONST_JOKE_POSITION_EXTRA, position)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJokeDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        handleExtra()
    }

    private fun handleExtra() {
        position = intent.getIntExtra(CONST_JOKE_POSITION_EXTRA, 1)
        if (position == -1) {
            handleError(position)
        } else {
            val item = generator.jokes[position] as? Joke
            if (item != null) {
                setupJokeData(item)
            } else {
                handleError(position)
            }
        }
    }

    private fun setupJokeData(joke: Joke) {
        with(binding) {
            category.text = joke.category
            question.text = joke.question
            answer.text = joke.answer
        }
    }

    private fun handleError(position: Int) {
        Toast.makeText(this, "Invalid Joke data, $position", Toast.LENGTH_SHORT).show()
        finish()  // Закрытие активности
    }
}