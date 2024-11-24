package com.kristallik.jokeapp.ui.add_joke.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import com.kristallik.jokeapp.R
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator.jokes
import com.kristallik.jokeapp.databinding.FragmentAddJokeBinding
import com.kristallik.jokeapp.ui.add_joke.AddJokePresenter
import com.kristallik.jokeapp.ui.add_joke.AddJokeView


class AddJokeFragment : Fragment(), AddJokeView {
    private var _binding: FragmentAddJokeBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: AddJokePresenter
    private lateinit var category: String
    private lateinit var question: String
    private lateinit var answer: String

    companion object {
        const val BUNDLE_KEY = "BUNDLE_KEY"
        const val REQUEST_KEY = "REQUEST_KEY"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAddJokeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = AddJokePresenter(this)
        presenter.loadCategories()
        binding.saveButton.setOnClickListener {
            category = (binding.menu.editText as? AutoCompleteTextView)?.text.toString()
            println(category)
            question = binding.question.text.toString()
            answer = binding.answer.text.toString()
            presenter.onSaveButtonClicked(category, question, answer)
        }

    }

    override fun showCategories(categories: List<String>) {
        val adapterMenu = ArrayAdapter(requireContext(), R.layout.spinner_item, categories)
        (binding.menu.editText as? AutoCompleteTextView)?.setAdapter(adapterMenu)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun saveJoke(message: String) {
        val newJoke = Joke(jokes.size, category, question, answer, "user")
        setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to newJoke))
        requireActivity().supportFragmentManager.popBackStack()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}