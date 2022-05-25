package com.n0lik.sample.genres.data

import com.n0lik.sample.genres.model.Genre

interface GenreRepository {

    suspend fun getGenres(): List<Genre>
}