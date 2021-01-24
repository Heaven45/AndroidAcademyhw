package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Collection(
    val backdrop_path: String?,
    val id: Int,
    val name: String,
    val poster_path: String?
)