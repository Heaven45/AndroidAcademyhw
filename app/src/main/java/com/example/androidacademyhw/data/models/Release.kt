package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Release(
    val iso_3166_1: String,
    val release_dates: List<ReleaseData>
)