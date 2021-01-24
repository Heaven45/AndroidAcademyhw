package com.example.androidacademyhw.movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.androidacademyhw.FragmentClickListener
import com.example.androidacademyhw.R
import com.example.androidacademyhw.Tags
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.data.loadMovies
import com.example.androidacademyhw.databinding.FragmentMoviesListBinding
import com.example.androidacademyhw.movies.MoviesAdapter.OnMovieClickListener

import com.example.androidacademyhw.details.FragmentMoviesDetails
import kotlinx.coroutines.*


class FragmentMoviesList : Fragment(), OnMovieClickListener {

    private var fragmentMoviesClickListener: FragmentClickListener? = null

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private var coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private lateinit var moviesAdapter: MoviesAdapter

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

        coroutineScope.launch() {
            val movies = getMovies(requireContext())
            initAdapter(movies)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
        coroutineScope.cancel()
    }

    private fun initAdapter(movies: List<Movie>) {
        moviesAdapter = MoviesAdapter(
                object : MoviesAdapter.OnMovieClickListener {
                    override fun onMovieClick(movie: Movie) {
                        onMovieDetailsClicked(movie)
                    }
                }
            )

        moviesAdapter.apply {
            setHasStableIds(true)
            submitList(movies)
        }
        binding.listMovies.apply {
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }

    suspend fun getMovies(context: Context): List<Movie> {
        return withContext(Dispatchers.IO) {
            loadMovies(context)
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

    fun onMovieDetailsClicked(movie: Movie) {
        navigateToDetails(movie)
    }

    companion object {
        fun newInstance(): FragmentMoviesList {
            val args = Bundle()
            val fragment = FragmentMoviesList()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onMovieClick(movie: Movie) {
        navigateToDetails(movie)
    }
}
