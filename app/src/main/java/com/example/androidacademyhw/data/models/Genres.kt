package com.emikhalets.androidacademy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Genres(
        val genres: List<Genre>
)