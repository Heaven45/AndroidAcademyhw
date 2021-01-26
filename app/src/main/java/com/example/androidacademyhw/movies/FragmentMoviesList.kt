package com.example.androidacademyhw.movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidacademyhw.FragmentClickListener
import com.example.androidacademyhw.R
import com.example.androidacademyhw.Tags
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.databinding.FragmentMoviesListBinding
import com.example.androidacademyhw.details.FragmentMoviesDetails
import com.example.androidacademyhw.movies.MoviesAdapter.OnMovieClickListener

class FragmentMoviesList : Fragment(), OnMovieClickListener {

    private var fragmentMoviesClickListener: FragmentClickListener? = null

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

        viewModel.movies.observe(viewLifecycleOwner, { movies -> moviesAdapter.submitList(movies) })
        initAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        moviesAdapter = MoviesAdapter(this)
        binding.listMovies.apply {
            adapter = moviesAdapter
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        binding.listMovies.adapter = null
    }

    override fun onMovieClick(movie: Movie) {
        navigateToDetails(movie)
    }

    companion object {
        const val TAG = "Movies_List_Log"

        fun newInstance(): FragmentMoviesList {
            val args = Bundle()
            val fragment = FragmentMoviesList()
            fragment.arguments = args
            return fragment
        }
    }
}
