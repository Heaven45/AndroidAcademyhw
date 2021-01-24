package com.example.androidacademyhw.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.androidacademyhw.R
import com.example.androidacademyhw.buildImageUrl
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.data.api.ApiFactory
import com.example.androidacademyhw.data.models.Genre
import com.example.androidacademyhw.data.models.MovieResult
import com.example.androidacademyhw.databinding.ItemMovieBinding
import com.example.androidacademyhw.databinding.MovieBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MoviesAdapter(private val clickListener: OnMovieClickListener)
    : ListAdapter<Movie, MoviesAdapter.ViewHolder>(MovieDiffCallback()) {

    private val mainCoroutineContext = CoroutineScope(Dispatchers.Main)
    private val coroutineContext = CoroutineScope(Dispatchers.IO)

    private val api = ApiFactory.get()
    var genres: List<Genre>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, clickListener) }
    }

    inner class ViewHolder(private val binding: MovieBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: MovieResult, clickListener: OnMovieClickListener) {
            with(binding) {
                smallMoviePicture.load(item.poster_path?.let { buildImageUrl(it) }) {
                    crossfade(500)
                    placeholder(R.drawable.ic_launcher_foreground)
                    fallback(R.drawable.unlike)
                }

                genre.text = formatGenres(item.genre_ids)
                ratingbar.rating = item.vote_average.toFloat() / 2
                reviewAmount.text = root.context.getString(
                    R.string.movie_item_text_reviews, item.vote_count
                )
                movieNameText.text = item.title

                root.setOnClickListener { clickListener.onMovieClick(item.id) }

                val job = coroutineContext.async {
                    api.movie(item.id)
                }

                mainCoroutineContext.launch {
                    val response = job.await()
                    val releases = response.release_dates.results
                    movieDuration.text = root.context.getString(
                        R.string.movie_item_text_time,
                        response.runtime
                    )
                    age.text =
                        releases.find { it.iso_3166_1 == "RU" }?.release_dates?.get(0)?.certification
                            ?: releases.find { it.iso_3166_1 == "US" }?.release_dates?.get(0)?.certification
                }
            }
        }

        private fun formatGenres(ids: List<Int>): String {
            val sb = StringBuffer()
            for (i in ids.indices) {
                sb.append(genres?.get(i)?.name)
                if (i != ids.size - 1) sb.append(", ")
            }
            return sb.toString()
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movieId: Int)
    }
}
