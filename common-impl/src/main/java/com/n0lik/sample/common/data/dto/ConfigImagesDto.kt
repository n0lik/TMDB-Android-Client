package com.n0lik.sample.common.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ConfigImagesDto(
    @SerialName("images")
    val config: ConfigDto
) {

    @Serializable
    internal data class ConfigDto(
        @SerialName("backdrop_sizes")
        val backdropSizes: List<String>,
        @SerialName("base_url")
        val baseUrl: String,
        @SerialName("poster_sizes")
        val posterSizes: List<String>,
        @SerialName("secure_base_url")
        val secureBaseUrl: String,
    )
}