package com.n0lik.sample.movie.domain

import com.n0lik.common.test.dispatcher.TestAppDispatcher
import com.n0lik.common.test.response.CONTENT_NOT_FOUND_JSON
import com.n0lik.common.test.response.toResponseBody
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

@ExperimentalCoroutinesApi
internal class MovieDetailInteractorTest {

    private val mockMovieRepository = mockk<MovieRepository>()
    private val testDispatcher = TestAppDispatcher()
    private val movieInteractor = MovieDetailInteractorImpl(
        appDispatcher = testDispatcher,
        movieRepository = mockMovieRepository
    )

    private val movieId = Movie.Id(1)

    @Test
    fun `should return correct data`() = runTest {
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } returns Movie(
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
        coEvery {
            mockMovieRepository.getSimilarMovies(movieId)
        } returns listOf(
            Movie(
                id = Movie.Id(2),
                title = "Title_2",
                overview = "Overview_2",
                _posterPath = "path_2",
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
            movie = Movie(
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
            similarMovies = listOf(
                Movie(
                    id = Movie.Id(2),
                    title = "Title_2",
                    overview = "Overview_2",
                    _posterPath = "path_2",
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

        val actual = movieInteractor.getMovieDetail(movieId)

        assertEquals(expected, actual)
    }

    @Test(expected = UnknownHostException::class)
    fun `should throw exception when can't get similar movies`() = runTest {
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } returns Movie(
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
        coEvery { mockMovieRepository.getSimilarMovies(movieId) } throws UnknownHostException()

        movieInteractor.getMovieDetail(movieId)
    }

    @Test(expected = HttpException::class)
    fun `should throw exception when movie doesn't exist`() = runTest {
        coEvery {
            mockMovieRepository.getMovie(movieId)
        } throws HttpException(Response.error<MovieDto>(404, CONTENT_NOT_FOUND_JSON.toResponseBody()))
        movieInteractor.getMovieDetail(movieId)
    }
}