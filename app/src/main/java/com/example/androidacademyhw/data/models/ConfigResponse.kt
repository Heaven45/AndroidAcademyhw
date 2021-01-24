package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ConfigResponse(
    val change_keys: List<String>,
    val images: ImageConfig
)