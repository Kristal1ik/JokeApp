package com.kristallik.jokeapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristallik.jokeapp.R
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.data.JokeGenerator.jokes
import com.kristallik.jokeapp.databinding.FragmentJokeListBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.ui.add_joke.fragment.AddJokeFragment
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsFragment
import com.kristallik.jokeapp.ui.main.MainPresenter
import com.kristallik.jokeapp.ui.main.MainView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


// TODO: Не показывать загурзку, если не было изменений в списке шуток
class JokeListFragment : Fragment(), MainView {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!
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
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJokeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter = MainPresenter(this)
        setFragmentResultListener("requestKey") { _, bundle ->
            val newJoke = bundle.getParcelable<Joke>("bundleKey")
            newJoke?.let {
                jokes.add(it)
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            presenter.loadJokes()
            createRecyclerViewList()
        }
        savedInstanceState?.let {
            currentPosition = it.getInt(CONST_CURRENT_POSITION, 0)
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
    }

    override suspend fun showJokes(jokes: ArrayList<Joke>) = coroutineScope {
        val job = launch {
            binding.progressBar.visibility = View.VISIBLE
            delay(2000L)
            binding.progressBar.visibility = View.INVISIBLE
        }
        job.join()
        binding.errorText.text = ""
        adapter.submitList(jokes)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}