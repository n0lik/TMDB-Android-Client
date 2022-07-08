package com.n0lik.sample.movie.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieImagesDto(
    @SerialName("backdrops")
    val backdrops: List<ImageDto>,
    @SerialName("posters")
    val posters: List<ImageDto>
) {

    @Serializable
    data class ImageDto(
        @SerialName("file_path")
        val filePath: String,
        @SerialName("height")
        val height: Int,
        @SerialName("width")
        val width: Int
    )
}