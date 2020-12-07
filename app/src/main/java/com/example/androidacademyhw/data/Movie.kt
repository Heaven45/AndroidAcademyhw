package com.example.androidacademyhw.data

import java.io.Serializable

data class Movie(
    val name: String,
    val pg: Int,
    val tags: String,
    var rating: Int,
    val reviewCount: Int,
    val duration: Int,
    val image: Int,
    val storyLine: String,
    var isLike: Boolean,
    val actors: List<Actor>
) : Serializable