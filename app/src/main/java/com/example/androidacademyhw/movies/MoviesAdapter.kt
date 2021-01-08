package com.example.androidacademyhw.movies

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.example.androidacademyhw.R
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.databinding.MovieBinding

class MoviesAdapter(
    private val clickListener: OnMovieClickListener

) : ListAdapter<Movie, MoviesAdapter.ViewHolder>(MovieDiffCallback()) {

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
                        ContextCompat.getDrawable(root.context, R.drawable.ic_like)
                    )
                } else {
                    heart.setImageDrawable(
                        ContextCompat.getDrawable(root.context, R.drawable.ic_unlike)
                    )
                }

                Glide.with(root.context)
                    .load(item.poster)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_unlike)
                    .into(smallMoviePicture)

                age.text = root.context.getString(R.string.movie_item_text_pg, item.minimumAge)
                genre.text = item.genres.map { it.name }.joinToString()
                ratingbar.rating = item.ratings.toFloat()
                reviewAmount.text =
                    root.context.getString(R.string.movie_item_text_reviews, item.numberOfRatings)
                movieNameText.text = item.title
                movieDuration.text = root.context.getString(
                    R.string.movie_item_text_time,
                    item.runtime
                )

                heart.setOnClickListener { likeClickListener?.invoke(position, item.isLike) }
                root.setOnClickListener { clickListener.onMovieClick(item) }
            }
        }
    }

    interface OnMovieClickListener {
        fun onMovieClick(movie: Movie)
    }
}
