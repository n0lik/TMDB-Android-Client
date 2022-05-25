package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.model.Language
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.ProductionCompany
import javax.inject.Inject

internal class MovieMapper
@Inject constructor(
    private val genreMapper: MapperTo<GenreDto, Genre>,
    private val productionCompanyMapper: MapperTo<ProductionCompanyDto, ProductionCompany>,
    private val languageMapper: MapperTo<LanguageDto, Language>
) : MapperTo<MovieDto, Movie> {

    override fun mapTo(model: MovieDto): Movie {
        val genres = model.genres?.let { genreMapper.mapToList(it) }
        val productionCompanies = model.productionCompanies?.let { productionCompanyMapper.mapToList(it) }
        val spokenLanguages = model.spokenLanguages?.let { languageMapper.mapToList(it) }

        return Movie(
            id = Movie.Id(model.id ?: 0),
            title = model.title,
            genres = genres,
            budget = model.budget,
            imdbId = model.imdbId,
            originalTitle = model.originalTitle,
            _posterPath = model.posterPath,
            overview = model.overview,
            releaseDate = model.releaseDate,
            video = model.isVideo,
            voteAverage = model.voteAverage,
            voteCount = model.voteCount,
            productionCompanies = productionCompanies,
            languages = spokenLanguages
        )
    }
}