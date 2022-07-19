package com.n0lik.sample.common.model

import kotlinx.serialization.Serializable

@Serializable
data class ImageConfig(
    val backdropSizes: List<String>,
    val posterSizes: List<String>,
    val secureBaseUrl: String,
)