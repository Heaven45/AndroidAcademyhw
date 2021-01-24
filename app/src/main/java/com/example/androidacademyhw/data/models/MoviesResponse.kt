package com.emikhalets.androidacademy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class MoviesResponse(
        var dates: Dates? = null,
        val page: Int,
        val results: List<MovieResult>,
        val total_pages: Int,
        val total_results: Int
)