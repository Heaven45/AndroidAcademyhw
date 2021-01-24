package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Company(
    val id: Int,
    val logo_path: String?,
    val name: String,
    val origin_country: String
)