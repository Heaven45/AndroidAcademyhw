package com.example.androidacademyhw.movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.androidacademyhw.R
import com.example.androidacademyhw.Tags
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.databinding.FragmentMoviesListBinding
import com.example.androidacademyhw.details.FragmentMoviesDetails
import com.example.androidacademyhw.movies.MoviesAdapter.OnMovieClickListener
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class FragmentMoviesList : Fragment(), OnMovieClickListener {

    private var fragmentMoviesClickListener: FragmentMoviesClickListener? = null

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private val viewModel: MoviesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initAdapter()
        initSpinner()

        viewModel.genres.observe(viewLifecycleOwner, { genres ->
            moviesAdapter.genres = genres

            lifecycleScope.launch {
                viewModel.movies("popular").collectLatest { data -> moviesAdapter.submitData(data) }
            }

            binding.spinner.onItemSelectedListener = object : CustomItemSelectedListener() {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    when (position) {
                        0 -> setListType("popular")
                        1 -> setListType("upcoming")
                        2 -> setListType("top")
                        3 -> setListType("now")
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        fragmentMoviesClickListener = null
        _binding = null
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(this)
        binding.listMovies.adapter = moviesAdapter
    }

    private fun initSpinner() {
        val spinnerAdapter = ArrayAdapter.createFromResource(
            requireContext(),
            R.array.listTypes,
            R.layout.item_spinner
        )
        spinnerAdapter.setDropDownViewResource(R.layout.item_spinner)
        binding.spinner.adapter = spinnerAdapter
    }

    private fun setListType(type: String) {
        lifecycleScope.launch {
            binding.listMovies.scrollToPosition(0)
            viewModel.movies(type).collectLatest { data -> moviesAdapter.submitData(data) }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentMoviesClickListener) fragmentMoviesClickListener = context
    }

    override fun onDetach() {
        super.onDetach()
        fragmentMoviesClickListener = null
    }

    private fun navigateToDetails(movie: Movie) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_movies_list, FragmentMoviesDetails.newInstance(movie))
            .addToBackStack(Tags.BACK_STACK_TAG)
            .commit()
    }

    override fun onMovieClick(movieId: Int) {
        fragmentMoviesClickListener?.onMovieClicked(movieId)
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            val args = Bundle()
            val fragment = FragmentMoviesList()
            fragment.arguments = args
            return fragment
        }
    }
}
