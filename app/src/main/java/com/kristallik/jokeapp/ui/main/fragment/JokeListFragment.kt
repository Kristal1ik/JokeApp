package com.kristallik.jokeapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kristallik.jokeapp.R
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator.jokes
import com.kristallik.jokeapp.databinding.FragmentJokeListBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment.Companion.BUNDLE_KEY
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment.Companion.REQUEST_KEY
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsFragment
import com.kristallik.jokeapp.ui.main.MainPresenter
import com.kristallik.jokeapp.ui.main.MainView
import kotlinx.coroutines.launch


class JokeListFragment : Fragment(), MainView {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: MainPresenter

    private val adapter = JokeListAdapter { position ->
        currentPosition = position
        val jokeDetailsFragment = JokeDetailsFragment.newInstance(position)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, jokeDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    private var currentPosition: Int = 0
    private var currentJokesList: ArrayList<Joke> = ArrayList()

    companion object {
        const val CONST_CURRENT_POSITION = "CURRENT_POSITION"
        const val JOKES = "JOKES"
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
            presenter.loadJokes(requireContext())
        }

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == adapter.itemCount - 1) {
                    viewLifecycleOwner.lifecycleScope.launch {
                        binding.progressBar.visibility = View.VISIBLE
                        presenter.loadMoreJokes(requireContext())
                        binding.progressBar.visibility = View.INVISIBLE
                    }
                }
            }
        })
        // Восстановление данных
        savedInstanceState?.let {
            currentPosition = it.getInt(CONST_CURRENT_POSITION, 0)
            currentJokesList = it.getParcelableArrayList<Joke>(JOKES) ?: ArrayList()
            adapter.submitList(currentJokesList)
        }
        binding.recyclerview.scrollToPosition(currentPosition)

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
            outState.putParcelableArrayList(JOKES, jokes)
        }
    }

    override suspend fun showJokes(jokes: ArrayList<Joke>) {
        binding.progressBar.visibility = View.INVISIBLE
        binding.errorText.text = ""
        adapter.submitList(jokes.toList())
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
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
}