package com.n0lik.sample.movie.model

data class MovieDetail(
    val isFavorite: Boolean = false,
    val movie: Movie,
    val similarMovies: List<Movie>?,
    val backdrops: List<TmdbImage>,
    val posters: List<TmdbImage>
)