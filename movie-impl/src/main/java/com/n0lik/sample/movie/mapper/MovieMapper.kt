package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.DateMapper
import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.common.mapper.Mapper1
import com.n0lik.sample.common.model.ImageConfig
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import com.n0lik.sample.movie.builder.TmdbImageFactory
import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.model.Language
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.ProductionCompany
import javax.inject.Inject

internal class MovieMapper
@Inject constructor(
    private val genreMapper: Mapper0<GenreDto, Genre>,
    private val productionCompanyMapper: Mapper0<ProductionCompanyDto, ProductionCompany>,
    private val languageMapper: Mapper0<LanguageDto, Language>,
    private val imageFactory: TmdbImageFactory,
    private val dateMapper: DateMapper
) : Mapper1<MovieDto, ImageConfig, Movie> {

    override fun mapTo(t1: MovieDto, t2: ImageConfig): Movie {
        val genres = t1.genres?.let { genreMapper.mapToList(it) }
        val productionCompanies = t1.productionCompanies?.let { productionCompanyMapper.mapToList(it) }
        val spokenLanguages = t1.spokenLanguages?.let { languageMapper.mapToList(it) }

        return Movie(
            id = Movie.Id(t1.id ?: 0),
            title = t1.title,
            genres = genres,
            backdropImage = imageFactory.createBackdrop(t2, t1.backdropPath),
            posterImage = imageFactory.createPoster(t2, t1.posterPath),
            budget = t1.budget,
            imdbId = t1.imdbId,
            originalTitle = t1.originalTitle,
            overview = t1.overview,
            releaseDate = dateMapper.mapToReleaseDate(t1.releaseDate),
            video = t1.isVideo,
            voteAverage = t1.voteAverage,
            voteCount = t1.voteCount,
            productionCompanies = productionCompanies,
            languages = spokenLanguages
        )
    }
}