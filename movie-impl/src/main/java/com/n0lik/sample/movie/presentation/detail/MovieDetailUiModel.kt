package com.n0lik.sample.movie.presentation.detail

import com.n0lik.sample.movie.model.Movie

data class MovieDetailUiModel(
    val isFavorite: Boolean,
    val state: State,
    val movie: Movie?,
    val similarMovies: List<Movie>?
) {

    companion object {

        fun loading() = MovieDetailUiModel(
            isFavorite = false,
            state = Loading,
            movie = null,
            similarMovies = null
        )

        fun success(
            movie: Movie?,
            similarMovies: List<Movie>?,
            isFavorite: Boolean,
        ) = MovieDetailUiModel(isFavorite, Success, movie, similarMovies)
    }
}

sealed class State
object Success : State()
object Loading : State()
data class Error(val throwable: Throwable) : State()