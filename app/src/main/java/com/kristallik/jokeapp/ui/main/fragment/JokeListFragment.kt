package com.kristallik.jokeapp.ui.main.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kristallik.jokeapp.R
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator.jokes
import com.kristallik.jokeapp.data.PreferencesProvider
import com.kristallik.jokeapp.databinding.FragmentJokeListBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment.Companion.BUNDLE_KEY
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment.Companion.REQUEST_KEY
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsFragment
import com.kristallik.jokeapp.ui.main.MainPresenter
import com.kristallik.jokeapp.ui.main.MainView
import kotlinx.coroutines.launch


class JokeListFragment : Fragment(), MainView, PreferencesProvider {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: MainPresenter
    private var count: Int = 0

    private val adapter = JokeListAdapter { position ->
        currentPosition = position
        val jokeDetailsFragment = JokeDetailsFragment.newInstance(position)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, jokeDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    private var currentPosition: Int = 0

    companion object {
        const val CONST_CURRENT_POSITION = "CURRENT_POSITION"
        const val JOKES_COUNT = "JOKES_COUNT"
        const val PREF = "PREF"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MainPresenter(this)
        count = getJokesCount()
        createRecyclerViewList()

        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val newJoke = bundle.getParcelable<Joke>(BUNDLE_KEY)
            newJoke?.let {
                jokes.add(it)
                adapter.submitList(jokes.toList())
                binding.errorText.text = ""
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            presenter.loadJokes()
        }

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        binding.progressBar.visibility = View.VISIBLE
                        presenter.loadMoreJokes()
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        })
        // Восстановление данных
        savedInstanceState?.let {
            currentPosition = it.getInt(CONST_CURRENT_POSITION, 0)
            count = it.getInt(JOKES_COUNT, 0)
        }
        binding.recyclerview.scrollToPosition(currentPosition) // Прокручиваем к сохраненной позиции

        binding.addActionButton.setOnClickListener {
            presenter.onActionButtonClicked()
        }
    }

    private fun createRecyclerViewList() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (_binding != null) {
            currentPosition =
                (binding.recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            outState.putInt(CONST_CURRENT_POSITION, currentPosition)
        }
        setJokesCount(count)
    }

    override suspend fun showJokes(jokes: ArrayList<Joke>) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.errorText.text = ""
        adapter.submitList(jokes.toList())
    }

    override fun showError(errorMessage: String) {
        binding.errorText.text = errorMessage
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun addJoke() {
        val addJokeFragment = AddJokeFragment()
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, addJokeFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun onResume() {
        super.onResume()
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun getJokesCount(): Int {
        val sharedPreferences =
            requireContext().getSharedPreferences(PREF, Context.MODE_PRIVATE)
        return sharedPreferences.getInt(JOKES_COUNT, 0)
    }

    override fun setJokesCount(count: Int) {
        val sharedPreferences = requireContext().getSharedPreferences(PREF, Context.MODE_PRIVATE)
        sharedPreferences.edit().putInt(JOKES_COUNT, count).apply()
    }
}