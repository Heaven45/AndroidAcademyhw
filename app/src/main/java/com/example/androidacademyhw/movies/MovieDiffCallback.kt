package com.example.androidacademyhw.movies

import androidx.recyclerview.widget.DiffUtil
import com.example.androidacademyhw.data.Movie

class MovieDiffCallback : DiffUtil.ItemCallback<Movie>() {

    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem.name == newItem.name

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
        oldItem == newItem
}