package com.n0lik.sample.movie.domain

import com.n0lik.common.test.ext.mockkRelaxed
import com.n0lik.common.test.response.CONTENT_NOT_FOUND_JSON
import com.n0lik.common.test.response.toResponseBody
import com.n0lik.sample.common.model.Image
import com.n0lik.sample.movie.data.FavoriteRepository
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieDetailInteractorImplTest {

    private val mockMovieRepository = mockk<MovieRepository>()
    private val mockFavoriteRepository = mockk<FavoriteRepository>()
    private val movieId = Movie.Id(1)

    private val movieInteractor = MovieDetailInteractorImpl(
        movieRepository = mockMovieRepository,
        favoriteRepository = mockFavoriteRepository
    )

    @Test
    fun `should return correct data when movie NOT added to favorite`() = runTest {
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } returns Movie(
            id = Movie.Id(1),
            title = "Title",
            overview = "Overview",
            posterImage = Image.Poster(
                path = "/poster.jpg",
                sizes = emptyList(),
                secureBaseUrl = "https://test.com"
            ),
            backdropImage = Image.Backdrop(
                path = "/backdrop.jpg",
                sizes = emptyList(),
                secureBaseUrl = "https://test.com"
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
        coEvery { mockFavoriteRepository.isFavorite(movieId) } returns flowOf(false)
        coEvery {
            mockMovieRepository.getSimilarMovies(movieId)
        } returns listOf(
            Movie(
                id = Movie.Id(2),
                title = "Title_2",
                overview = "Overview_2",
                posterImage = Image.Poster(
                    path = "/poster2.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
                ),
                backdropImage = Image.Backdrop(
                    path = "/backdrop2.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
                ),
                budget = 20_000,
                genres = emptyList(),
                imdbId = "1234",
                languages = emptyList(),
                originalTitle = "Original title #2",
                productionCompanies = emptyList(),
                releaseDate = null,
                voteAverage = 1.1,
                voteCount = 2,
                video = true
            )
        )

        val expected = MovieDetail(
            isFavorite = false,
            movie = Movie(
                id = Movie.Id(1),
                title = "Title",
                overview = "Overview",
                posterImage = Image.Poster(
                    path = "/poster.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
                ),
                backdropImage = Image.Backdrop(
                    path = "/backdrop.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
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
            similarMovies = listOf(
                Movie(
                    id = Movie.Id(2),
                    title = "Title_2",
                    overview = "Overview_2",
                    posterImage = Image.Poster(
                        path = "/poster2.jpg",
                        sizes = emptyList(),
                        secureBaseUrl = "https://test.com"
                    ),
                    backdropImage = Image.Backdrop(
                        path = "/backdrop2.jpg",
                        sizes = emptyList(),
                        secureBaseUrl = "https://test.com"
                    ),
                    budget = 20_000,
                    genres = emptyList(),
                    imdbId = "1234",
                    languages = emptyList(),
                    originalTitle = "Original title #2",
                    productionCompanies = emptyList(),
                    releaseDate = null,
                    voteAverage = 1.1,
                    voteCount = 2,
                    video = true
                )
            )
        )

        val actual = movieInteractor.getMovieDetail(movieId).first()

        assertEquals(expected, actual)
    }

    @Test
    fun `should return correct data when movie added to favorite`() = runTest {
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } returns Movie(
            id = Movie.Id(1),
            title = "Title",
            overview = "Overview",
            posterImage = Image.Poster(
                path = "/poster.jpg",
                sizes = emptyList(),
                secureBaseUrl = "https://test.com"
            ),
            backdropImage = Image.Backdrop(
                path = "/backdrop.jpg",
                sizes = emptyList(),
                secureBaseUrl = "https://test.com"
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
        coEvery { mockFavoriteRepository.isFavorite(movieId) } returns flowOf(true)
        coEvery {
            mockMovieRepository.getSimilarMovies(movieId)
        } returns listOf(
            Movie(
                id = Movie.Id(2),
                title = "Title_2",
                overview = "Overview_2",
                posterImage = Image.Poster(
                    path = "/poster.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
                ),
                backdropImage = Image.Backdrop(
                    path = "/backdrop.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
                ),
                budget = 20_000,
                genres = emptyList(),
                imdbId = "1234",
                languages = emptyList(),
                originalTitle = "Original title #2",
                productionCompanies = emptyList(),
                releaseDate = null,
                voteAverage = 1.1,
                voteCount = 2,
                video = true
            )
        )

        val expected = MovieDetail(
            isFavorite = true,
            movie = Movie(
                id = Movie.Id(1),
                title = "Title",
                overview = "Overview",
                posterImage = Image.Poster(
                    path = "/poster.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
                ),
                backdropImage = Image.Backdrop(
                    path = "/backdrop.jpg",
                    sizes = emptyList(),
                    secureBaseUrl = "https://test.com"
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
            similarMovies = listOf(
                Movie(
                    id = Movie.Id(2),
                    title = "Title_2",
                    overview = "Overview_2",
                    posterImage = Image.Poster(
                        path = "/poster.jpg",
                        sizes = emptyList(),
                        secureBaseUrl = "https://test.com"
                    ),
                    backdropImage = Image.Backdrop(
                        path = "/backdrop.jpg",
                        sizes = emptyList(),
                        secureBaseUrl = "https://test.com"
                    ),
                    budget = 20_000,
                    genres = emptyList(),
                    imdbId = "1234",
                    languages = emptyList(),
                    originalTitle = "Original title #2",
                    productionCompanies = emptyList(),
                    releaseDate = null,
                    voteAverage = 1.1,
                    voteCount = 2,
                    video = true
                )
            )
        )

        val actual = movieInteractor.getMovieDetail(movieId).first()

        assertEquals(expected, actual)
    }

    @Test(expected = UnknownHostException::class)
    fun `should throw exception when can't get similar movies`() = runTest {
        coEvery { mockFavoriteRepository.isFavorite(movieId) } returns flowOf(false)
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } returns mockkRelaxed()
        coEvery { mockMovieRepository.getSimilarMovies(movieId) } throws UnknownHostException()

        movieInteractor.getMovieDetail(movieId).first()
    }

    @Test(expected = HttpException::class)
    fun `should throw exception when movie doesn't exist`() = runTest {
        coEvery { mockFavoriteRepository.isFavorite(movieId) } returns flowOf(false)
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } throws HttpException(Response.error<MovieDto>(404, CONTENT_NOT_FOUND_JSON.toResponseBody()))
        movieInteractor.getMovieDetail(movieId).first()
    }

    @Test
    fun `should change favorite state correctly when movie NOT added to favorite`() = runTest {
        coEvery { movieInteractor.changeFavoriteState(true, movieId) } returns Unit
        coEvery { mockMovieRepository.getMovie(movieId) } returns mockkRelaxed()
        coEvery { mockMovieRepository.getSimilarMovies(movieId) } returns mockkRelaxed()

        movieInteractor.changeFavoriteState(true, movieId)

        coVerify { mockFavoriteRepository.add(movieId) }
    }

    @Test
    fun `should change favorite state correctly when movie added to favorite`() = runTest {
        coEvery { movieInteractor.changeFavoriteState(false, movieId) } returns Unit
        coEvery { mockMovieRepository.getMovie(movieId) } returns mockkRelaxed()
        coEvery { mockMovieRepository.getSimilarMovies(movieId) } returns mockkRelaxed()

        movieInteractor.changeFavoriteState(false, movieId)

        coVerify { mockFavoriteRepository.remove(movieId) }
    }
}