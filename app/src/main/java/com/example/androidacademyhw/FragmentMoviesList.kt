package com.example.androidacademyhw

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager

interface  FragmentMoviesClickListener {
    fun onMovieDetailsClicked()
}

class FragmentMoviesList : Fragment() {

    private var fragmentMoviesClickListener: FragmentMoviesClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_list, container, false)

        view?.findViewById<ImageView>(R.id.small_movie_picture)?.apply {
            setOnClickListener { fragmentMoviesClickListener?.onMovieDetailsClicked() }
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentMoviesClickListener) {
            fragmentMoviesClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentMoviesClickListener = null
    }


    companion object {
        fun newInstance() : FragmentMoviesList {
            val fragment = FragmentMoviesList()
            return fragment
        }
    }

}