package com.n0lik.sample.movie.data.api.dto

import com.n0lik.sample.genres.data.api.dto.GenreDto
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MovieDto(
    @SerialName("id")
    val id: Int?,
    @SerialName("adult")
    val adult: Boolean?,
    @SerialName("budget")
    val budget: Int?,
    @SerialName("genres")
    val genres: List<GenreDto>?,
    @SerialName("homepage")
    val homepage: String?,
    @SerialName("imdb_id")
    val imdbId: String?,
    @SerialName("original_language")
    val originalLanguage: String?,
    @SerialName("original_title")
    val originalTitle: String?,
    @SerialName("overview")
    val overview: String?,
    @SerialName("popularity")
    val popularity: Double?,
    @SerialName("poster_path")
    val posterPath: String?,
    @SerialName("production_companies")
    val productionCompanies: List<ProductionCompanyDto>?,
    @SerialName("production_countries")
    val productionCountries: List<ProductionCountryDto>?,
    @SerialName("release_date")
    val releaseDate: String?,
    @SerialName("revenue")
    val revenue: Int?,
    @SerialName("runtime")
    val runtime: Int?,
    @SerialName("spoken_languages")
    val spokenLanguages: List<LanguageDto>?,
    @SerialName("status")
    val status: String?,
    @SerialName("tagline")
    val tagline: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("video")
    val isVideo: Boolean?,
    @SerialName("vote_average")
    val voteAverage: Double?,
    @SerialName("vote_count")
    val voteCount: Int?
)