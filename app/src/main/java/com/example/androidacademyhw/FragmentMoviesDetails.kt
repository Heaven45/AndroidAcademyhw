package com.example.androidacademyhw

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class FragmentMoviesDetails : Fragment() {
    private var fragmentClickListener: MainActivity.FragmentClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_movies_details, container, false)

        view?.findViewById<ImageView>(R.id.button_back_image)?.apply {
            fragmentClickListener?.onBackFragmentClicked()
        }

        view?.findViewById<TextView>(R.id.button_back_text)?.apply {
            fragmentClickListener?.onBackFragmentClicked()
        }

        return view
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is MainActivity.FragmentClickListener) {
            fragmentClickListener = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        fragmentClickListener = null
    }
}