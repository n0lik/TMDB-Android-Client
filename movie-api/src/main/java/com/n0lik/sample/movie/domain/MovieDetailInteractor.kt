package com.n0lik.sample.movie.domain

import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail

interface MovieDetailInteractor {

    suspend fun getMovieDetail(movieId: Movie.Id): MovieDetail
}