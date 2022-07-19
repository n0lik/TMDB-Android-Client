package com.n0lik.sample.movie.model

data class MovieImages(
    val backdrops: List<MeasuredImage.Backdrop>,
    val posters: List<MeasuredImage.Poster>,
)