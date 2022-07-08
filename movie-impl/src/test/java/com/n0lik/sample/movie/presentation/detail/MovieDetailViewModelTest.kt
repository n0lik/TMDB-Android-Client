package com.n0lik.sample.movie.presentation.detail

import com.n0lik.common.test.ext.mockkRelaxed
import com.n0lik.sample.movie.domain.MovieDetailInteractor
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieDetail
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieDetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()

    private val mockMovieDetailInteractor = mockk<MovieDetailInteractor>()

    @Before
    fun prepare() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun release() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should load movie`() = runTest {
        coEvery { mockMovieDetailInteractor.getMovieDetail(Movie.Id(1)) } returns mockkRelaxed()

        MovieDetailViewModel(1, mockMovieDetailInteractor)

        coVerify { mockMovieDetailInteractor.getMovieDetail(Movie.Id(1)) }
    }

    @Test
    fun `should start loading when open screen`() = runTest {
        coEvery { mockMovieDetailInteractor.getMovieDetail(Movie.Id(1)) } returns mockkRelaxed()
        val expected = MovieDetailUiModel(
            isFavorite = false,
            state = Loading,
            movie = null,
            similarMovies = null,
            posters = null,
            backdrops = null
        )
        val viewModel = MovieDetailViewModel(1, mockMovieDetailInteractor)
        val actual = viewModel.viewState.first()

        assertEquals(expected, actual)
    }

    @Test
    fun `should update screen when load data`() = runTest {
        coEvery {
            mockMovieDetailInteractor.getMovieDetail(Movie.Id(1))
        } returns flowOf(
            MovieDetail(
                isFavorite = true,
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
                similarMovies = null,
                posters = listOf(),
                backdrops = listOf()
            )
        )

        val expected = MovieDetailUiModel(
            isFavorite = true,
            state = Success,
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
            similarMovies = null,
            posters = listOf(),
            backdrops = listOf()
        )
        val viewModel = MovieDetailViewModel(1, mockMovieDetailInteractor)
        viewModel.load()

        val actual = viewModel.viewState.first()

        assertEquals(expected, actual)
    }

    @Test
    fun `should update screen with error`() = runTest {
        val exception = UnknownHostException()
        coEvery { mockMovieDetailInteractor.getMovieDetail(Movie.Id(1)) } throws exception
        val viewModel = MovieDetailViewModel(1, mockMovieDetailInteractor)
        val expected = MovieDetailUiModel(
            isFavorite = false,
            movie = null,
            state = Error(exception),
            backdrops = null,
            posters = null,
            similarMovies = null
        )

        val actual = viewModel.viewState.first()

        assertEquals(expected, actual)
    }

    @Test
    fun `should call update method when movie was NOT added but click favorite button`() = runTest {
        coEvery { mockMovieDetailInteractor.getMovieDetail(Movie.Id(1)) } returns flowOf(
            MovieDetail(
                isFavorite = false,
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
                similarMovies = null,
                posters = listOf(),
                backdrops = listOf()
            )
        )
        val viewModel = MovieDetailViewModel(1, mockMovieDetailInteractor)

        viewModel.onFavoriteClick()

        coVerify {
            mockMovieDetailInteractor.changeFavoriteState(true, Movie.Id(1))
        }
    }

    @Test
    fun `should call update method when movie was added but click favorite button`() = runTest {
        coEvery { mockMovieDetailInteractor.getMovieDetail(Movie.Id(1)) } returns flowOf(
            MovieDetail(
                isFavorite = true,
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
                similarMovies = null,
                posters = listOf(),
                backdrops = listOf()
            )
        )
        val viewModel = MovieDetailViewModel(1, mockMovieDetailInteractor)

        viewModel.onFavoriteClick()

        coVerify {
            mockMovieDetailInteractor.changeFavoriteState(false, Movie.Id(1))
        }
    }
}