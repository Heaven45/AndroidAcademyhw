package com.example.androidacademyhw.data.repository

import com.example.androidacademyhw.data.api.ApiService

class MovieListRepository(private val api: ApiService) {

    suspend fun getGenres() = api.genres()
}