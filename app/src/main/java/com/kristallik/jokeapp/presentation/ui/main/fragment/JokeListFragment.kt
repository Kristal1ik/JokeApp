package com.kristallik.jokeapp.presentation.ui.main.fragment

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
import com.kristallik.jokeapp.domain.model.Joke
import com.kristallik.jokeapp.data.generator.JokeGenerator.jokes
import com.kristallik.jokeapp.data.repository.JokeRepository
import com.kristallik.jokeapp.data.repository.JokeRepositoryImpl
import com.kristallik.jokeapp.data.source.local.JokeDatabase
import com.kristallik.jokeapp.data.source.remote.JokeApiService
import com.kristallik.jokeapp.databinding.FragmentJokeListBinding
import com.kristallik.jokeapp.domain.usecase.AddNetworkJokeUseCase
import com.kristallik.jokeapp.domain.usecase.GetJokesUseCase
import com.kristallik.jokeapp.domain.usecase.GetNetworkJokesUseCase
import com.kristallik.jokeapp.presentation.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.presentation.ui.add_joke.fragment.AddJokeFragment
import com.kristallik.jokeapp.presentation.ui.joke_details.fragment.JokeDetailsFragment
import com.kristallik.jokeapp.presentation.ui.main.MainPresenter
import com.kristallik.jokeapp.presentation.ui.main.MainView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class JokeListFragment : Fragment(), MainView {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var jokeApiService: JokeApiService
    @Inject
    lateinit var database: JokeDatabase
    @Inject
    lateinit var jokeRepository: JokeRepository
    private lateinit var presenter: MainPresenter

    private val adapter = JokeListAdapter { position ->
        val jokeDetailsFragment = JokeDetailsFragment.newInstance(position)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, jokeDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    private var currentPosition: Int = 0

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


        val getJokesUseCase = GetJokesUseCase(jokeRepository)
        val getNetworkJokesUseCase = GetNetworkJokesUseCase(jokeRepository)
        val addNetworkJokeUseCase = AddNetworkJokeUseCase(jokeRepository)

        presenter = MainPresenter(
            view = this,
            getJokesUseCase = getJokesUseCase,
            getNetworkJokesUseCase = getNetworkJokesUseCase,
            addNetworkJokeUseCase = addNetworkJokeUseCase
        )
        createRecyclerViewList()

        setFragmentResultListener(AddJokeFragment.REQUEST_KEY) { _, bundle ->
            val newJoke = bundle.getParcelable<Joke>(AddJokeFragment.BUNDLE_KEY)
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
            adapter.submitList(jokes.toList())
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

    override suspend fun showJokes(jokes: List<Joke>) {
        binding.progressBar.visibility = View.INVISIBLE
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

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
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
