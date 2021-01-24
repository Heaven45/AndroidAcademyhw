package com.emikhalets.androidacademy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Credits(
    val cast: List<Cast>,
    val crew: List<Crew>,
    val id: Int
)