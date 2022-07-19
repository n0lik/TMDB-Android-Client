package com.n0lik.sample.movie.data

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.domain.ConfigRepository
import com.n0lik.sample.common.mapper.Mapper1
import com.n0lik.sample.common.model.ImageConfig
import com.n0lik.sample.movie.data.api.MovieApi
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRepositoryImpl
@Inject constructor(
    private val dispatcher: AppDispatcher,
    private val movieApi: MovieApi,
    private val movieMapper: Mapper1<MovieDto, ImageConfig, Movie>,
    private val configRepository: ConfigRepository
) : MovieRepository {

    override suspend fun getMovie(movieId: Movie.Id): Movie {
        return withContext(dispatcher.io) {
            val config = async { configRepository.getImageConfig() }
            val movies = movieApi.getMovieById(movieId.id)
            movies.let {
                movieMapper.mapTo(it, config.await())
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Movie.Id): List<Movie> {
        return withContext(dispatcher.io) {
            val config = async { configRepository.getImageConfig() }
            movieApi.getSimilarMovies(movieId = movieId.id).let {
                movieMapper.mapToList(it.data, config.await())
            }
        }
    }
}