package com.example.androidacademyhw.movies

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import com.example.androidacademyhw.FragmentClickListener
import com.example.androidacademyhw.R
import com.example.androidacademyhw.Tags
import com.example.androidacademyhw.data.Actor
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.databinding.FragmentMoviesListBinding
import com.example.androidacademyhw.details.FragmentMoviesDetails

class FragmentMoviesList : Fragment() {

    private var fragmentMoviesClickListener: FragmentClickListener? = null

    private var _binding: FragmentMoviesListBinding? = null
    private val binding get() = _binding!!

    private lateinit var moviesAdapter: MoviesAdapter
    private val actors = mutableListOf<Actor>()
    private val movies = mutableListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)
        _binding = FragmentMoviesListBinding.inflate(inflater, container, false)
        return binding.root

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initData()
        initAdapter()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initAdapter() {
        moviesAdapter =
            MoviesAdapter(
                object : MoviesAdapter.OnMovieClickListener {
                    override fun onMovieClick(movie: Movie) {
                        onMovieDetailsClicked(movie)
                    }

                }
            )

        moviesAdapter.likeClickListener = { position, isLike ->

        }

        moviesAdapter.apply {
            setHasStableIds(true)
            submitList(movies)
        }
        binding.listMovies.apply {
            setHasFixedSize(true)
            adapter = moviesAdapter
        }
    }

    private fun initData() {
        actors.add(
            Actor(
                "Robert Downey Jr.",
                R.drawable.actor1
            )
        )
        actors.add(
            Actor(
                "Chris Evans",
                R.drawable.actor2
            )
        )
        actors.add(
            Actor(
                "Mark Ruffalo",
                R.drawable.actor3
            )
        )
        actors.add(
            Actor(
                "Chris Hemsworth",
                R.drawable.actor4
            )
        )

        movies.add(
            Movie(
                "Avengers: End Game",
                13,
                "Action, Adventure, Drama",
                4,
                125,
                137,
                R.drawable.smaller_movie,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                false,
                actors
            )
        )
        movies.add(
            Movie(
                "Tenet",
                16,
                "Action, Sci-Fi, Thriller",
                5,
                98,
                97,
                R.drawable.tenet,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                true,
                actors
            )
        )
        movies.add(
            Movie(
                "Black Widow",
                13,
                "Action, Adventure, Sci-Fi",
                4,
                38,
                102,
                R.drawable.black_widow,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                false,
                actors
            )
        )
        movies.add(
            Movie(
                "Wonder Woman 1984",
                13,
                "Action, Adventure, Fantasy",
                5,
                74,
                120,
                R.drawable.wonderwomen,
                "After the devastating events of Avengers: Infinity War, the universe is in ruins. With the help of remaining allies, the Avengers assemble once more in order to reverse Thanos' actions and restore balance to the universe.",
                false,
                actors
            )
        )
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentClickListener) {
            fragmentMoviesClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentMoviesClickListener = null
    }

//    fun onMovieDetailsClicked(movie: Movie) {
//        childFragmentManager.beginTransaction()
//            .addToBackStack(null)
//            .replace(R.id.fragment_movies_list,
//                FragmentMoviesDetails().apply {
//                    arguments = bundleOf("MovieDetail" to movie) // Вытащить по этому ключу
//                }
//            )
//            .commit()
//    }


    private fun navigateToDetails(movie: Movie) {
        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.fragment_movies_list, FragmentMoviesDetails.newInstance(movie))
            .addToBackStack(Tags.BACK_STACK_TAG)
            .commit()
    }

    fun onMovieDetailsClicked(movie: Movie) {
        navigateToDetails(movie)
    }

    fun onLikeClick(position: Int, isLike: Boolean) {
        movies[position].isLike = !isLike
        moviesAdapter.submitList(null)
        moviesAdapter.submitList(movies.toMutableList())
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