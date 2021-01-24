package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Country(
    val iso_3166_1: String,
    val name: String
)