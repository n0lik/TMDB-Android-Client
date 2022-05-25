package com.n0lik.sample.movie.data.api

import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto

internal interface MovieKtorApi {

    suspend fun getPopularMovies(page: Int): PagedListDto<MovieDto>
}