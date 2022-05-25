package com.n0lik.sample.movie.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal class LanguageDto(
    @SerialName("iso_639_1")
    val iso: String?,
    @SerialName("name")
    val name: String?
)