package com.example.androidacademyhw.data.repository

import com.example.androidacademyhw.data.api.ApiService

class MoviesDetailsRepository(private val api: ApiService) {

    suspend fun getMovie(id: Int) = api.movie(id)
    suspend fun getCast(id: Int) = api.movieCast(id)
}