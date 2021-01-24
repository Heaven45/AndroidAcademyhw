package com.example.androidacademyhw.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseResult(
        val results: List<Release>
)