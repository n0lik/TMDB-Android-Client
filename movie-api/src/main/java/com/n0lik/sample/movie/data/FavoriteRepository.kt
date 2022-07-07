package com.n0lik.sample.movie.data

import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {

    suspend fun add(movieId: Movie.Id)
    suspend fun remove(movieId: Movie.Id)

    fun isFavorite(movieId: Movie.Id): Flow<Boolean>
}