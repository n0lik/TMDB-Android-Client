package com.n0lik.sample.movie.data

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.MovieApi
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieRepositoryImpl
@Inject constructor(
    private val dispatcher: AppDispatcher,
    private val movieApi: MovieApi,
    private val movieMapper: MapperTo<MovieDto, Movie>
) : MovieRepository {

    override suspend fun getMovie(movieId: Movie.Id): Movie {
        return withContext(dispatcher.io) {
            movieApi.getMovieById(movieId.id).let {
                movieMapper.mapTo(it)
            }
        }
    }

    override suspend fun getSimilarMovies(movieId: Movie.Id): List<Movie> {
        return withContext(dispatcher.io) {
            movieApi.getSimilarMovies(movieId = movieId.id).let {
                movieMapper.mapToList(it.data)
            }
        }
    }
}