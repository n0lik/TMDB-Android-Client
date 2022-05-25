package com.n0lik.sample.genres.data.api

import com.n0lik.sample.genres.data.api.dto.GenresDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

internal class GenresApiImpl
@Inject constructor(
    private val client: HttpClient
) : GenresApi {

    override suspend fun getMovieGenres(): GenresDto {
        return client.get("/3/genre/movie/list").body()
    }
}

internal interface GenresApi {

    suspend fun getMovieGenres(): GenresDto
}