package com.kristallik.jokeapp.ui.joke_details

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.FragmentJokeDetailsBinding

class JokeDetailsFragment : Fragment(), JokeDetailsView {
    private var _binding: FragmentJokeDetailsBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: JokeDetailsPresenter

    companion object {
        private const val ARG_JOKE_POSITION = "joke_position"

        fun newInstance(position: Int): JokeDetailsFragment {
            val fragment = JokeDetailsFragment()
            val args = Bundle()
            args.putInt(ARG_JOKE_POSITION, position)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = JokeDetailsPresenter(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Получаем позицию шутки из аргументов фрагмента и загружаем ее детали через презентер
        arguments?.getInt(ARG_JOKE_POSITION)?.let { position ->
            Log.d("JokeDetailsFragment", "Loading joke at position: $position")
            presenter.loadJokeDetails(position)
        } ?: run {
            Log.e("JokeDetailsFragment", "No joke position passed")
            showErrorAndCloseScreen("Invalid joke position!")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun showJokeInfo(joke: Joke) {
        if (_binding == null) return // Проверка на null перед доступом к binding
        with(binding) {
            category.text = joke.category
            question.text = joke.question
            answer.text = joke.answer
        }
    }

    override fun showErrorAndCloseScreen(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
        requireActivity().supportFragmentManager.popBackStack()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Освобождаем ресурсы привязки при уничтожении представления.
    }
}