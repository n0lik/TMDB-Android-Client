package com.n0lik.sample.movie.data.api

import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import javax.inject.Inject

internal class MovieKtorApiImpl
@Inject constructor(
    private val client: HttpClient
) : MovieKtorApi {

    override suspend fun getPopularMovies(page: Int): PagedListDto<MovieDto> {
        return client.get("3/movie/popular") {
            parameter("page", page)
        }.body()
    }
}