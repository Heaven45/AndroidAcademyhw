package com.example.androidacademyhw.data.api

import com.example.androidacademyhw.data.models.*
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("configuration")
    suspend fun configuration(): ConfigResponse

    @GET("movie/now_playing")
    suspend fun movieNowPlaying(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET("movie/popular")
    suspend fun moviePopular(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET("movie/top_rated")
    suspend fun movieTopRated(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET("movie/upcoming")
    suspend fun movieUpcoming(
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US"
    ): MoviesResponse

    @GET("movie/{movie_id}")
    suspend fun movie(
        @Path("movie_id") id: Int,
        @Query("append_to_response") appendRequest: String = "release_dates",
        @Query("language") language: String = "en-US"
    ): Movie

    @GET("movie/{movie_id}/credits")
    suspend fun movieCast(
        @Path("movie_id") id: Int,
        @Query("language") language: String = "en-US"
    ): Credits

    @GET("person/{person_id}")
    suspend fun person(
        @Path("person_id") id: Int,
        @Query("append_to_response") appendRequest: String = "",
        @Query("language") language: String = "en-US"
    ): Person

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("page") page: Int = 1,
        @Query("language") language: String = "en-US",
        @Query("include_adult") include_adult: Boolean = true,
        @Query("year") year: Int,
        @Query("primary_release_year") releaseYear: Int
    ): MoviesResponse

    @GET("genre/movie/list")
    suspend fun genres(
        @Query("language") language: String = "en-US"
    ): Genres
}