package com.example.androidacademyhw.movies

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.androidacademyhw.data.repository.MovieListRepository
import com.example.androidacademyhw.data.repository.MoviesPagingSource
import com.example.androidacademyhw.data.api.ApiFactory
import com.example.androidacademyhw.data.models.Genre
import kotlinx.coroutines.launch

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = MovieListRepository(ApiFactory.get())

    private val _genres = MutableLiveData<List<Genre>>()
    val genres get(): LiveData<List<Genre>> = _genres

    init {
        viewModelScope.launch {
            val response = repository.getGenres()
            _genres.postValue(response.genres)
        }
    }

    fun movies(query: String) =
        Pager(PagingConfig(20)) { MoviesPagingSource(ApiFactory.get(), query) }
            .flow.cachedIn(viewModelScope)
}