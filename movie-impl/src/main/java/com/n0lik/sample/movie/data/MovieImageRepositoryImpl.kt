package com.n0lik.sample.movie.data

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.movie.data.api.MovieImageApi
import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieImages
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class MovieImageRepositoryImpl
@Inject constructor(
    private val appDispatcher: AppDispatcher,
    private val movieImageApi: MovieImageApi,
    private val mapper: Mapper0<MovieImagesDto, MovieImages>
) : MovieImageRepository {

    override suspend fun getImages(movieId: Movie.Id): MovieImages {
        return withContext(appDispatcher.io) {
            movieImageApi.getMovieImages(movieId.id).let {
                mapper.mapTo(it)
            }
        }
    }
}