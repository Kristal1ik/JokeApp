package com.kristallik.jokeapp.ui.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kristallik.jokeapp.R
import com.kristallik.jokeapp.data.Joke
import com.kristallik.jokeapp.databinding.FragmentJokeListBinding
import com.kristallik.jokeapp.recycler.adapters.JokeListAdapter
import com.kristallik.jokeapp.ui.joke_details.JokeDetailsFragment
import com.kristallik.jokeapp.ui.main.MainPresenter
import com.kristallik.jokeapp.ui.main.MainView

class JokeListFragment : Fragment(), MainView {

    private var _binding: FragmentJokeListBinding? = null
    private val binding get() = _binding!!
    private lateinit var presenter: MainPresenter

    private val adapter = JokeListAdapter { position ->
        val jokeDetailsFragment = JokeDetailsFragment.newInstance(position)
        parentFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                jokeDetailsFragment
            )
            .addToBackStack(null)
            .commit()
    }

    private var currentPosition: Int = 0

    companion object {
        const val CONST_CURRENT_POSITION = "CURRENT_POSITION"
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

        savedInstanceState?.let {
            currentPosition = it.getInt(CONST_CURRENT_POSITION, 0)
        }

        presenter.loadJokes()
        binding.recyclerview.scrollToPosition(currentPosition)
    }

    private fun createRecyclerViewList() {
        binding.recyclerview.adapter = adapter
        binding.recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        // Предотвращение NullPointerException при повороте
        if (_binding != null) {
            currentPosition =
                (binding.recyclerview.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            outState.putInt(CONST_CURRENT_POSITION, currentPosition)
        }
    }

    override fun showJokes(jokes: ArrayList<Joke>) {
        adapter.submitList(jokes)
    }

    override fun showError(errorMessage: String) {
        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}