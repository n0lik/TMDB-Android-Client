package com.n0lik.sample.movie.domain

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieDetailInteractorImpl
@Inject constructor(
    private val appDispatcher: AppDispatcher,
    private val movieRepository: MovieRepository
) : MovieDetailInteractor {

    override suspend fun getMovieDetail(movieId: Movie.Id): MovieDetail {
        return withContext(appDispatcher.io) {
            val movie = async { movieRepository.getMovie(movieId) }
            val similarMovies = async { movieRepository.getSimilarMovies(movieId) }
            MovieDetail(
                movie = movie.await(),
                similarMovies = similarMovies.await()
            )
        }
    }
}