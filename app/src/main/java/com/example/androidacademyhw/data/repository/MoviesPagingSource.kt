package com.example.androidacademyhw.data.repository

import android.util.Log
import androidx.paging.PagingSource
import com.example.androidacademyhw.data.api.ApiService
import com.example.androidacademyhw.data.models.MovieResult

class MoviesPagingSource(
    private val api: ApiService,
    private val query: String
) : PagingSource<Int, MovieResult>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MovieResult> {
        return try {
            val pageNumber = params.key ?: 1
            val nextPageNumber = pageNumber + 1

            val response = when (query) {
                "upcoming" -> api.movieUpcoming(page = pageNumber)
                "popular" -> api.moviePopular(page = pageNumber)
                "top" -> api.movieTopRated(page = pageNumber)
                else -> api.movieNowPlaying(page = pageNumber)
            }

            LoadResult.Page(response.results, null, nextPageNumber)

        } catch (ex: Exception) {
            Log.e("MoviesPagingSource", "Paging source: ${ex.message}")
            ex.printStackTrace()
            LoadResult.Error(ex)
        }
    }
}