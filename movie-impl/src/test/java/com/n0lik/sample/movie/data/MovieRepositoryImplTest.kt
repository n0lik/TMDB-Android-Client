package com.n0lik.sample.movie.data

import com.n0lik.common.test.dispatcher.TestAppDispatcher
import com.n0lik.sample.movie.data.api.MovieApi
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.mapper.MovieMapper
import com.n0lik.sample.movie.model.Movie
import io.mockk.Called
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertThrows
import org.junit.Test

@ExperimentalCoroutinesApi
class MovieRepositoryImplTest {

    private val dispatcher = TestAppDispatcher()
    private val movieApiMock = mockk<MovieApi>()
    private val movieMapper = mockk<MovieMapper>()

    private val repository = MovieRepositoryImpl(
        dispatcher = dispatcher,
        movieApi = movieApiMock,
        movieMapper = movieMapper
    )

    @Test
    fun `should load movie by id`() = runTest {
        coEvery { movieApiMock.getMovieById(1) } returns mockk(relaxed = true)
        every { movieMapper.mapTo(any()) } returns mockk()

        val actual = repository.getMovie(Movie.Id(1))

        assertNotNull(actual)
        coVerify {
            movieApiMock.getMovieById(1)
            movieMapper.mapTo(any())
        }
    }

    @Test
    fun `should return null when movie doesn't exist`() = runTest {
        coEvery { movieApiMock.getMovieById(1) } returns null

        val actual = repository.getMovie(Movie.Id(1))

        assertNull(actual)
        coVerify { movieApiMock.getMovieById(1) }
        verify { movieMapper.mapTo(any()) wasNot Called }
    }

    @Test
    fun `should throw exception when load movie by id`() {
        coEvery { movieApiMock.getMovieById(1) } throws UnknownHostException()

        assertThrows(UnknownHostException::class.java) {
            runTest {
                repository.getMovie(Movie.Id(1))
            }
        }

        verify(exactly = 0) { movieMapper.mapTo(any()) }
    }

    @Test
    fun `should load similar movies by id`() = runTest {
        val model = PagedListDto<MovieDto>(
            page = 1,
            data = emptyList(),
            totalPages = 2,
            totalResults = 3
        )
        coEvery { movieApiMock.getSimilarMovies(1) } returns model
        every { movieMapper.mapToList(any()) } returns emptyList()
        val expected = emptyList<Movie>()

        val actual = repository.getSimilarMovies(Movie.Id(1))

        assertEquals(expected, actual)
        coVerify {
            movieApiMock.getSimilarMovies(1)
            movieMapper.mapToList(emptyList())
        }
    }
}