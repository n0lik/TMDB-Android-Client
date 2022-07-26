package com.n0lik.sample.movie.mapper

import com.n0lik.common.test.ext.mockkRelaxed
import com.n0lik.sample.common.mapper.DateMapper
import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.common.model.Image
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
import io.mockk.every
import org.junit.Assert
import org.junit.Test

internal class MovieMapperTest {

    private val genreMapperMock = mockkRelaxed<Mapper0<GenreDto, Genre>> {
        every { mapToList(any()) } returns emptyList()
    }
    private val productionCompanyMapperMock = mockkRelaxed<Mapper0<ProductionCompanyDto, ProductionCompany>> {
        every { mapToList(any()) } returns emptyList()
    }
    private val languageMapperMock = mockkRelaxed<Mapper0<LanguageDto, Language>> {
        every { mapToList(any()) } returns emptyList()
    }
    private val defaultImageConfig = ImageConfig(
        backdropSizes = listOf(
            "w320", "w500", "w720"
        ),
        posterSizes = listOf(
            "w320", "w500", "w720"
        ),
        secureBaseUrl = "https://test.com/"
    )
    private val dateMapper = DateMapper()

    private val mapper = MovieMapper(
        genreMapper = genreMapperMock,
        productionCompanyMapper = productionCompanyMapperMock,
        languageMapper = languageMapperMock,
        imageFactory = TmdbImageFactory(),
        dateMapper = dateMapper
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
            posterPath = "/poster.jpg",
            backdropPath = "/backdrop.jpg",
            budget = 10_000,
            genres = emptyList(),
            imdbId = "123",
            originalTitle = "Original title",
            productionCompanies = emptyList(),
            productionCountries = emptyList(),
            isVideo = true,
            revenue = null,
            releaseDate = "2022-01-01",
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
            posterImage = Image.Poster(
                path = "/poster.jpg",
                sizes = listOf(
                    "w320", "w500", "w720"
                ),
                secureBaseUrl = "https://test.com/"
            ),
            backdropImage = Image.Backdrop(
                path = "/backdrop.jpg",
                sizes = listOf(
                    "w320", "w500", "w720"
                ),
                secureBaseUrl = "https://test.com/"
            ),
            budget = 10_000,
            genres = emptyList(),
            imdbId = "123",
            languages = emptyList(),
            originalTitle = "Original title",
            productionCompanies = emptyList(),
            releaseDate = "2022",
            voteAverage = 1.0,
            voteCount = 1,
            video = true
        )

        val actual = mapper.mapTo(moveDto, defaultImageConfig)

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
                posterPath = "/poster1.jpg",
                backdropPath = "/backdrop1.jpg",
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
                posterPath = "/poster2.jpg",
                backdropPath = "/backdrop2.jpg",
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
                posterImage = Image.Poster(
                    path = "/poster1.jpg",
                    sizes = listOf(
                        "w320", "w500", "w720"
                    ),
                    secureBaseUrl = "https://test.com/"
                ),
                backdropImage = Image.Backdrop(
                    path = "/backdrop1.jpg",
                    sizes = listOf(
                        "w320", "w500", "w720"
                    ),
                    secureBaseUrl = "https://test.com/"
                ),
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
                posterImage = Image.Poster(
                    path = "/poster2.jpg",
                    sizes = listOf(
                        "w320", "w500", "w720"
                    ),
                    secureBaseUrl = "https://test.com/"
                ),
                backdropImage = Image.Backdrop(
                    path = "/backdrop2.jpg",
                    sizes = listOf(
                        "w320", "w500", "w720"
                    ),
                    secureBaseUrl = "https://test.com/"
                ),
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

        val actual = mapper.mapToList(list, defaultImageConfig)

        Assert.assertEquals(expected, actual)
    }
}