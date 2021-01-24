package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class Error(
    val status_code: Int,
    val status_message: String,
    val success: Boolean?
)