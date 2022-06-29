package com.n0lik.sample.movie.data

import com.n0lik.sample.movie.model.Movie

interface MovieRepository {

    suspend fun getMovie(movieId: Movie.Id): Movie

    suspend fun getSimilarMovies(movieId: Movie.Id): List<Movie>
}