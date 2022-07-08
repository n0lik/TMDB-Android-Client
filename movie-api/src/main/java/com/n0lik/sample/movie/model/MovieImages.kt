package com.n0lik.sample.movie.model

data class MovieImages(
    val backdrops: List<TmdbImage>,
    val posters: List<TmdbImage>,
)

data class TmdbImage(
    val imageType: ImageType,
    val path: String,
    val height: Int,
    val width: Int
) {

    fun getFullPath() = "https://image.tmdb.org/t/p/w500$path"

    enum class ImageType { POSTER, BACKDROP }
}