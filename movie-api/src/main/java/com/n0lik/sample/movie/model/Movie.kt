package com.n0lik.sample.movie.model

import com.n0lik.sample.common.model.Image
import com.n0lik.sample.genres.model.Genre

data class Movie(
    val id: Id,
    val title: String?,
    val overview: String?,
    val video: Boolean?,
    val genres: List<Genre>?,
    val budget: Int?,
    val originalTitle: String?,
    val posterImage: Image.Poster,
    val backdropImage: Image.Backdrop,
    val releaseDate: String?,
    val imdbId: String?,
    val voteAverage: Double?,
    val voteCount: Int?,
    val productionCompanies: List<ProductionCompany>?,
    val languages: List<Language>?
) {

    @JvmInline
    value class Id(val id: Int)
}