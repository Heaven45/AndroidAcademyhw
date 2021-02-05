package com.example.androidacademyhw.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidacademyhw.data.api.ApiFactory
import com.example.androidacademyhw.data.models.Credits
import com.example.androidacademyhw.data.models.Genre
import com.example.androidacademyhw.data.models.Movie
import com.example.androidacademyhw.data.repository.MoviesDetailsRepository
import kotlinx.coroutines.launch

class MoviesDetailsViewModel : ViewModel() {

    private val repository = MoviesDetailsRepository(ApiFactory.get())

    private val _movie = MutableLiveData<Movie>()
    val movie get(): LiveData<Movie> = _movie

    private val _cast = MutableLiveData<Credits>()
    val cast get(): LiveData<Credits> = _cast

    fun getMovie(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getMovie(id)
                _movie.postValue(result)
            } catch (ex: Exception) {
                Log.e("MoviesDetailsViewModel", "getMovie: ${ex.message}")
                ex.printStackTrace()
            }
        }
    }

    fun getCast(id: Int) {
        viewModelScope.launch {
            try {
                val result = repository.getCast(id)
                _cast.postValue(result)
            } catch (ex: Exception) {
                Log.e("MoviesDetailsViewModel", "getCast: ${ex.message}")
                ex.printStackTrace()
            }
        }
    }

    fun formatGenres(genres: List<Genre>): String {
        val sb = StringBuffer()
        for (i in genres.indices) {
            sb.append(genres[i].name)
            if (i != genres.size - 1) sb.append(", ")
        }
        return sb.toString()
    }
}