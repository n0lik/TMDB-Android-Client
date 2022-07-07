package com.n0lik.sample.movie.domain

import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import kotlinx.coroutines.flow.Flow

interface MovieDetailInteractor {

    suspend fun getMovieDetail(movieId: Movie.Id): Flow<MovieDetail>

    suspend fun changeFavoriteState(newState: Boolean, movieId: Movie.Id)
}