package com.example.androidacademyhw.details

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.load
import com.example.androidacademyhw.R
import com.example.androidacademyhw.buildImageUrl
import com.example.androidacademyhw.data.Actor
import com.example.androidacademyhw.data.loadActors
import com.example.androidacademyhw.data.models.Credits
import com.example.androidacademyhw.data.models.Movie
import com.example.androidacademyhw.databinding.FragmentMoviesDetailsBinding
import kotlinx.coroutines.*

class FragmentMoviesDetails : Fragment() {
    private var fragmentClickListener: FragmentDetailsClickListener? = null

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MoviesDetailsViewModel by viewModels()

    private lateinit var actorsAdapter: ActorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        arguments?.let {
            val id = it.getInt(KEY_MOVIE_ID)
            viewModel.getMovie(id)
            viewModel.getCast(id)
        }

        viewModel.movie.observe(viewLifecycleOwner, { movie -> updateUI(movie) })
        viewModel.cast.observe(viewLifecycleOwner, { credits -> updateCastUi(credits) })

        binding.buttonBackImage.setOnClickListener { fragmentClickListener?.onDetailsBackClicked() }
        binding.buttonBackText.setOnClickListener { fragmentClickListener?.onDetailsBackClicked() }
    }

    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun updateCastUi(credits: Credits) {
        if (credits.cast.isNotEmpty()) actorsAdapter.submitList(credits.cast)
    }

    private fun updateUI(movie: Movie) {
        with(binding) {
            movie.backdrop_path?.let { image ->
                movieLogoImage.load(buildImageUrl(image)) { crossfade(500) }
            }
            val releases = movie.release_dates.results
            age.text =
                releases.find { it.iso_3166_1 == "RU" }?.release_dates?.get(0)?.certification
                    ?: releases.find { it.iso_3166_1 == "US" }?.release_dates?.get(0)?.certification
            movieNameText.text = movie.title
            layoutTagsRating.textTagline.text = viewModel.formatGenres(movie.genres)
            layoutTagsRating.ratingBar.rating = movie.vote_average.toFloat() / 2
            layoutTagsRating.textReviews.text = getString(
                R.string.details_text_reviews, movie.vote_count
            )
            Storyline.text = movie.overview
        }
    }

    private fun initAdapter() {
        actorsAdapter = ActorsAdapter()
        actorsAdapter.setHasStableIds(true)
        binding.listActors.apply {
            setHasFixedSize(true)
            adapter = actorsAdapter
        }
    }

    suspend fun getActors(context: Context): List<Actor> {
        return withContext(Dispatchers.IO) {
            loadActors(context)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentDetailsClickListener) {
            fragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentClickListener = null
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        const val KEY_MOVIE_ID = "args_movie_id"

        fun newInstance(movieId: Int): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putSerializable(KEY_MOVIE_ID, movieId)
            fragment.arguments = args
            return fragment
        }
    }
}
