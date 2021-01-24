package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
        val genres: List<Genre>
)