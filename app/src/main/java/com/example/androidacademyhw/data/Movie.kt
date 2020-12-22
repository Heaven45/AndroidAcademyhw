package com.example.androidacademyhw.data

import java.io.Serializable

//data class Movie(
//    val id: Int?,
//    val title: String?,
//    val overview: String?,
//    val poster: String?,
//    val backdrop: String?,
//    val ratings: Float?,
//    val numberOfRatings: Int?,
//    val minimumAge: Int?,
//    val runtime: Int?,
//    val genres: List<Genre>?,
//    val actors: List<Actor>?,
//    var isLike: Boolean?,
//) : Serializable


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