package com.kristallik.jokeapp.ui.joke_details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.ActivityJokeDetailsBinding
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsView.Companion.VALUE_IF_ERROR

class JokeDetailsActivity : AppCompatActivity(), JokeDetailsView {
    private lateinit var binding: ActivityJokeDetailsBinding
    private lateinit var presenter: JokeDetailsPresenter
    private var position: Int = VALUE_IF_ERROR

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
        presenter = JokeDetailsPresenter(this)
        handleExtra()
    }

    private fun handleExtra() {
        position = intent.getIntExtra(CONST_JOKE_POSITION_EXTRA, 1)
        presenter.loadJokeDetails(position)
    }

    override fun showJokeInfo(joke: Joke) {
        with(binding) {
            category.text = joke.category
            question.text = joke.question
            answer.text = joke.answer
        }
    }

    override fun showErrorAndCloseScreen(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        finish()  // Закрытие активности
    }
}