package com.example.androidacademyhw

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.androidacademyhw.databinding.ActivityMainBinding
import com.example.androidacademyhw.details.FragmentDetailsClickListener
import com.example.androidacademyhw.details.FragmentMoviesDetails
import com.example.androidacademyhw.movies.FragmentMoviesClickListener

import com.example.androidacademyhw.movies.FragmentMoviesList


class MainActivity : AppCompatActivity(), FragmentMoviesClickListener,
    FragmentDetailsClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        savedInstanceState ?: supportFragmentManager.beginTransaction()
            .add(R.id.fragment_movies_list, FragmentMoviesList.newInstance())
            .commit()
    }

    override fun onDetailsBackClicked() {
        supportFragmentManager.popBackStack()
    }

    override fun onMovieClicked(movieId: Int) {
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_movies_list, FragmentMoviesDetails.newInstance(movieId))
            .addToBackStack(BACK_STACK_TAG)
            .commit()
    }

    companion object {
        const val BACK_STACK_TAG = "main_fragments_back_stack"
    }
}




