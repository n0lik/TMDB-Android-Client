package com.n0lik.sample.movie.data.api

import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Server's convention
 * @see START_PAGE_NUMBER - the first page number
 * @see PAGE_SIZE - API page size (immutable)
 */

const val PAGE_SIZE = 20
const val START_PAGE_NUMBER = 1

internal interface MovieApi {

    @GET("3/movie/{movieId}")
    suspend fun getMovieById(
        @Path("movieId") movieId: Int
    ): MovieDto

    @GET("3/movie/{movieId}/similar")
    suspend fun getSimilarMovies(
        @Path("movieId") movieId: Int
    ): PagedListDto<MovieDto>
}