package com.n0lik.sample.movie.domain

import com.n0lik.sample.movie.data.FavoriteRepository
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MovieDetailInteractorImpl
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository
) : MovieDetailInteractor {

    override suspend fun getMovieDetail(movieId: Movie.Id): Flow<MovieDetail> {
        return flow {
            val model = coroutineScope {
                val movie = async { movieRepository.getMovie(movieId) }
                val similarMovies = async { movieRepository.getSimilarMovies(movieId) }
                MovieDetail(
                    isFavorite = false,
                    movie = movie.await(),
                    similarMovies = similarMovies.await()
                )
            }
            emit(model)
        }.combine(favoriteRepository.isFavorite(movieId)) { movieDetailModel: MovieDetail, isFavorite: Boolean ->
            movieDetailModel.copy(isFavorite = isFavorite)
        }
    }

    override suspend fun changeFavoriteState(newState: Boolean, movieId: Movie.Id) {
        if (newState) {
            favoriteRepository.add(movieId)
        } else {
            favoriteRepository.remove(movieId)
        }
    }
}