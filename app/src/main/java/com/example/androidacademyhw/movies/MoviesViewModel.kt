package com.example.androidacademyhw.movies

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.androidacademyhw.data.Movie
import com.example.androidacademyhw.data.loadMovies
import kotlinx.coroutines.*

class MoviesViewModel(application: Application) : AndroidViewModel(application) {

    private val exceptionHandler = CoroutineExceptionHandler { canceledContext, exception ->
        val isActive = coroutineScope.isActive
        Log.d(
            FragmentMoviesList.TAG,
            "ExceptionHandler [Scope active:$isActive, canceledContext:$canceledContext]"
        )
        coroutineScope.launch {
            logException(exception)
        }
    }

    private var coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _movies = MutableLiveData<List<Movie>>()
    val movies get(): LiveData<List<Movie>> = _movies

    init {
        coroutineScope.launch(exceptionHandler) {
            val movies = loadMovies(application.applicationContext)
            _movies.postValue(movies)
        }
    }

    fun cancelCoroutines() = coroutineScope.cancel()

    private suspend fun logException(throwable: Throwable) = withContext(Dispatchers.IO) {
        Log.e(FragmentMoviesList.TAG, "${throwable.printStackTrace()}")
    }
}