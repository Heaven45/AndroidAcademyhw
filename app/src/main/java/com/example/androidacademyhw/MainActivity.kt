package com.example.androidacademyhw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MainActivity : AppCompatActivity(), FragmentClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .addToBackStack(null)
                .add(R.id.fragment_movies_list, FragmentMoviesList())
                .commit()
        }
    }

    override fun onMovieDetailsClicked() {
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.fragment_movies_list, FragmentMoviesDetails())
            .commit()
    }

        override fun onBackFragmentClicked() {
            supportFragmentManager.popBackStack()
        }
}

    interface FragmentClickListener {
        fun onMovieDetailsClicked()
        fun onBackFragmentClicked()
    }





