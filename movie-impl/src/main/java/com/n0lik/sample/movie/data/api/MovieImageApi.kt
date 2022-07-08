package com.n0lik.sample.movie.data.api

import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import retrofit2.http.GET
import retrofit2.http.Path

internal interface MovieImageApi {

    @GET("3/movie/{movie_id}/images")
    suspend fun getMovieImages(@Path("movie_id") movieId: Int): MovieImagesDto
}