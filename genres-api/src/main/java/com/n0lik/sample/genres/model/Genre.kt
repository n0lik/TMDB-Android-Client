package com.n0lik.sample.genres.model

data class Genre(
    val genreId: Id,
    val name: String?
) {
    @JvmInline
    value class Id(val id: Int = 0)
}