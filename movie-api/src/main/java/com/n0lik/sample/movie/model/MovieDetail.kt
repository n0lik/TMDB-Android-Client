package com.n0lik.sample.movie.model

data class MovieDetail(
    val movie: Movie,
    val similarMovies: List<Movie>?
)