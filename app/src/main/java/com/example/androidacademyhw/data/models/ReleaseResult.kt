package com.emikhalets.androidacademy.data.models

import kotlinx.serialization.Serializable

@Serializable
data class ReleaseResult(
        val results: List<Release>
)