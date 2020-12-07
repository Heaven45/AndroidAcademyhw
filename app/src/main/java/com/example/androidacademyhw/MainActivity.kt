package com.example.androidacademyhw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.androidacademyhw.databinding.ActivityMainBinding
import com.example.androidacademyhw.details.FragmentMoviesDetails
import com.example.androidacademyhw.movies.FragmentMoviesList


class MainActivity : AppCompatActivity(), FragmentClickListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        savedInstanceState ?: initMoviesList()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_movies_list,
                    FragmentMoviesList()
                )
                .commit()
        }
    }

        override fun onBackFragmentClicked() {
            supportFragmentManager.popBackStack()
        }
}

    interface FragmentClickListener {
        fun onBackFragmentClicked()
    }





