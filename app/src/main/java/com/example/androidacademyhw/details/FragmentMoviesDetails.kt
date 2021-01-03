package com.example.androidacademyhw.details

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidacademyhw.FragmentClickListener
import com.example.androidacademyhw.R
import com.example.androidacademyhw.Tags
import com.example.androidacademyhw.data.Actor
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.data.loadMovies
import com.example.androidacademyhw.databinding.FragmentMoviesDetailsBinding

class FragmentMoviesDetails : Fragment()  {
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

        val movie = arguments?.getSerializable(Tags.KEY_MOVIE) as Movie

        with(binding) {

            Glide.with(root.context)
                .load(movie.poster)
                .into(movieLogoImage)

            age.text = getString(R.string.details_item_text_pg, movie.minimumAge)
            movieNameText.text = movie.title
            genre.text = movie.genres.map{it.name}.joinToString()
            ratingbar.rating = movie.ratings.toFloat()
            reviewAmount.text = getString(R.string.details_text_reviews, movie.numberOfRatings)
            movieDescription.text = movie.overview
            initAdapter(movie.actors)
        }

        binding.buttonBackImage.setOnClickListener { navigateBack() }
        binding.buttonBackText.setOnClickListener { navigateBack() }
    }

    private fun navigateBack() {
        requireActivity().supportFragmentManager.popBackStack()
    }

    private fun initAdapter(actors: List<Actor>) {
        actorsAdapter = ActorsAdapter()
        actorsAdapter.apply {
            setHasStableIds(true)
            submitList(actors)
        }
        binding.listActors.apply {
            setHasFixedSize(true)
            adapter = actorsAdapter
            addOnItemTouchListener(object : RecyclerView.OnItemTouchListener {

                override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean =
                    false

                override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
                    if (e.action == MotionEvent.ACTION_BUTTON_PRESS) {
                    }
                }

                override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
                }
            })
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
            args.putSerializable(Tags.KEY_MOVIE, movie)
            fragment.arguments = args
            return fragment
        }
    }
}