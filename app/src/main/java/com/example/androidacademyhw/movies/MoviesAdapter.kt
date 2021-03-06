package com.example.androidacademyhw.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.androidacademyhw.R
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.databinding.MovieBinding

class MoviesAdapter(
    private val clickListener: OnMovieClickListener

) :
    ListAdapter<Movie, MoviesAdapter.ViewHolder>(MovieDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieBinding.inflate(inflater, parent, false)
        return ViewHolder(
            binding
        )
    }

    var likeClickListener: ((position: Int, isLike: Boolean) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position, clickListener, likeClickListener)
    }

    class ViewHolder(private val binding: MovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            item: Movie,
            position: Int,
            clickListener: OnMovieClickListener,
            likeClickListener: ((position: Int, isLike: Boolean) -> Unit)?
        ) {
            with(binding) {
                if (item.isLike) {
                    heart.setImageDrawable(
                        ContextCompat.getDrawable(root.context, R.drawable.like)
                    )
                } else {
                    heart.setImageDrawable(
                        ContextCompat.getDrawable(root.context, R.drawable.ic_unlike)
                    )
                }

                smallMoviePicture.setImageDrawable(ContextCompat.getDrawable(root.context, item.image))
                age.text = root.context.getString(R.string.movie_item_text_pg, item.pg)
                genre.text = item.tags
                ratingbar.rating = item.rating.toFloat()
                reviewAmount.text =
                    root.context.getString(R.string.movie_item_text_reviews, item.reviewCount)
                movieNameText.text = item.name
                movieDuration.text = root.context.getString(R.string.movie_item_text_time, item.duration)

                heart.setOnClickListener { likeClickListener?.invoke(position, item.isLike) }
                root.setOnClickListener { clickListener.onMovieClick(item) }
            }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}