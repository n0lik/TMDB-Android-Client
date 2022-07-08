package com.n0lik.sample.movie.domain

import com.n0lik.sample.movie.data.FavoriteRepository
import com.n0lik.sample.movie.data.MovieImageRepository
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

internal class MovieDetailInteractorImpl
@Inject constructor(
    private val movieRepository: MovieRepository,
    private val favoriteRepository: FavoriteRepository,
    private val movieImageRepository: MovieImageRepository
) : MovieDetailInteractor {

    override suspend fun getMovieDetail(movieId: Movie.Id): Flow<MovieDetail> = flow {
        val model = loadMovieDetail(movieId)
        emit(model)
    }
        .combine(favoriteRepository.isFavorite(movieId)) { movieDetailModel: MovieDetail, isFavorite: Boolean ->
            movieDetailModel.copy(isFavorite = isFavorite)
        }

    private suspend fun loadMovieDetail(movieId: Movie.Id): MovieDetail = coroutineScope {
        ensureActive()
        val movie = async { movieRepository.getMovie(movieId) }
        val similarMovies = async { movieRepository.getSimilarMovies(movieId) }
        val images = async { movieImageRepository.getImages(movieId) }
        val movieImage = images.await()

        MovieDetail(
            isFavorite = false,
            movie = movie.await(),
            similarMovies = similarMovies.await(),
            backdrops = movieImage.backdrops,
            posters = movieImage.posters
        )
    }

    override suspend fun changeFavoriteState(newState: Boolean, movieId: Movie.Id) {
        if (newState) {
            favoriteRepository.add(movieId)
        } else {
            favoriteRepository.remove(movieId)
        }
    }
}