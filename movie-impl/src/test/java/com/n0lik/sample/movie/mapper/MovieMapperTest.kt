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
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert
import org.junit.Test

internal class MovieMapperTest {

    private val genreMapperMock = mockk<MapperTo<GenreDto, Genre>>(relaxed = true) {
        every { mapToList(any()) } returns emptyList()
    }
    private val productionCompanyMapperMock = mockk<MapperTo<ProductionCompanyDto, ProductionCompany>>(relaxed = true) {
        every { mapToList(any()) } returns emptyList()
    }
    private val languageMapperMock = mockk<MapperTo<LanguageDto, Language>>(relaxed = true) {
        every { mapToList(any()) } returns emptyList()
    }

    private val mapper = MovieMapper(
        genreMapper = genreMapperMock,
        productionCompanyMapper = productionCompanyMapperMock,
        languageMapper = languageMapperMock
    )

    @Test
    fun `should map correctly`() {
        val moveDto = MovieDto(
            id = 1,
            adult = false,
            voteAverage = 1.0,
            voteCount = 1,
            title = "Title",
            overview = "Overview",
            posterPath = "path",
            budget = 10_000,
            genres = emptyList(),
            imdbId = "123",
            originalTitle = "Original title",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            isVideo = true,
            revenue = null,
            releaseDate = null,
            spokenLanguages = emptyList(),
            homepage = null,
            originalLanguage = null,
            runtime = null,
            popularity = null,
            status = null,
            tagline = null
        )
        val expected = Movie(
            id = Movie.Id(1),
            title = "Title",
            overview = "Overview",
            _posterPath = "path",
            budget = 10_000,
            genres = emptyList(),
            imdbId = "123",
            languages = emptyList(),
            originalTitle = "Original title",
            productionCompanies = emptyList(),
            releaseDate = null,
            voteAverage = 1.0,
            voteCount = 1,
            video = true
        )

        val actual = mapper.mapTo(moveDto)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `should map list correctly`() {
        val list = listOf(
            MovieDto(
                id = 1,
                adult = false,
                voteAverage = 1.0,
                voteCount = 1,
                title = "Title",
                overview = "Overview",
                posterPath = "path",
                budget = 10_000,
                genres = emptyList(),
                imdbId = "123",
                originalTitle = "Original title",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                isVideo = true,
                revenue = null,
                releaseDate = null,
                spokenLanguages = emptyList(),
                homepage = null,
                originalLanguage = null,
                runtime = null,
                popularity = null,
                status = null,
                tagline = null
            ),
            MovieDto(
                id = 2,
                adult = false,
                voteAverage = 1.0,
                voteCount = 1,
                title = "Title",
                overview = "Overview",
                posterPath = "path",
                budget = 10_000,
                genres = emptyList(),
                imdbId = "123",
                originalTitle = "Original title",
                productionCompanies = emptyList(),
                productionCountries = emptyList(),
                isVideo = true,
                revenue = null,
                releaseDate = null,
                spokenLanguages = emptyList(),
                homepage = null,
                originalLanguage = null,
                runtime = null,
                popularity = null,
                status = null,
                tagline = null
            )
        )
        val expected = listOf(
            Movie(
                id = Movie.Id(1),
                title = "Title",
                overview = "Overview",
                _posterPath = "path",
                budget = 10_000,
                genres = emptyList(),
                imdbId = "123",
                languages = emptyList(),
                originalTitle = "Original title",
                productionCompanies = emptyList(),
                releaseDate = null,
                voteAverage = 1.0,
                voteCount = 1,
                video = true
            ),
            Movie(
                id = Movie.Id(2),
                title = "Title",
                overview = "Overview",
                _posterPath = "path",
                budget = 10_000,
                genres = emptyList(),
                imdbId = "123",
                languages = emptyList(),
                originalTitle = "Original title",
                productionCompanies = emptyList(),
                releaseDate = null,
                voteAverage = 1.0,
                voteCount = 1,
                video = true
            )
        )

        val actual = mapper.mapToList(list)

        Assert.assertEquals(expected, actual)
    }
}