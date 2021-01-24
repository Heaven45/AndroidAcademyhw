package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseData(
    val certification: String,
    val iso_639_1: String?,
    val note: String,
    val release_date: String,
    val type: Int
)