package com.example.androidacademyhw

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity(), FragmentMoviesClickListener {
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

    fun onBackFragmentClicked() {
        supportFragmentManager.beginTransaction()
                .remove(FragmentMoviesList())
                .commit()
    }
    
    interface FragmentClickListener {
        fun onMovieDetailsClicked()
        fun onBackFragmentClicked()
    }
}





