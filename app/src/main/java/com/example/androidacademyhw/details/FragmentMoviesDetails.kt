package com.example.androidacademyhw.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.androidacademyhw.FragmentClickListener
import com.example.androidacademyhw.R
import com.example.androidacademyhw.Tags
import com.example.androidacademyhw.data.Actor
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.data.loadActors
import com.example.androidacademyhw.databinding.FragmentMoviesDetailsBinding
import kotlinx.coroutines.*
import java.io.Serializable

class FragmentMoviesDetails : Fragment() {
    private var fragmentClickListener: FragmentClickListener? = null

    private var _binding: FragmentMoviesDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var actorsAdapter: ActorsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        view?.findViewById<ImageView>(R.id.button_back_image)?.setOnClickListener {
            fragmentClickListener?.onBackFragmentClicked()
        }

        view?.findViewById<TextView>(R.id.button_back_text)?.setOnClickListener {
            fragmentClickListener?.onBackFragmentClicked()
        }

        _binding = FragmentMoviesDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapter()

        val movie = arguments?.getSerializable(Tags.KEY_MOVIE) as Movie
        updateUI(movie)

        binding.buttonBackImage.setOnClickListener { navigateBack() }
        binding.buttonBackText.setOnClickListener { navigateBack() }
    }

    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun updateUI(movie: Movie) {
        with(binding) {

            Glide.with(root.context)
                .load(movie.poster)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_unlike)
                .into(movieLogoImage)

            age.text = getString(
                com.example.androidacademyhw.R.string.details_item_text_pg,
                movie.minimumAge
            )
            movieNameText.text = movie.title
            genre.text = movie.genres.map { it.name }.joinToString()
            ratingbar.rating = movie.ratings.toFloat()
            reviewAmount.text = getString(
                com.example.androidacademyhw.R.string.details_text_reviews,
                movie.numberOfRatings
            )
            movieDescription.text = movie.overview

            if (movie.actors.isNotEmpty()) actorsAdapter.submitList(movie.actors)
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
        if (context is FragmentClickListener) {
            fragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentClickListener = null
    }

    companion object {
        fun newInstance(movie: Movie): FragmentMoviesDetails {
            val fragment = FragmentMoviesDetails()
            val args = Bundle()
            args.putSerializable(Tags.KEY_MOVIE, movie as Serializable)
            fragment.arguments = args
            return fragment
        }
    }
}
