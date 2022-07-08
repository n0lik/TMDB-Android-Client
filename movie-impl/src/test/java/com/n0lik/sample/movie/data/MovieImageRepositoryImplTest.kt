package com.n0lik.sample.movie.data

import com.n0lik.common.test.dispatcher.TestAppDispatcher
import com.n0lik.common.test.ext.mockkRelaxed
import com.n0lik.sample.movie.data.api.MovieImageApi
import com.n0lik.sample.movie.mapper.ImageMapper
import com.n0lik.sample.movie.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import java.net.UnknownHostException

@OptIn(ExperimentalCoroutinesApi::class)
internal class MovieImageRepositoryImplTest {

    private val mockMovieImageApi = mockk<MovieImageApi>()
    private val mapper = ImageMapper()

    private val repository = MovieImageRepositoryImpl(
        appDispatcher = TestAppDispatcher(),
        movieImageApi = mockMovieImageApi,
        mapper = mapper
    )

    @Test
    fun `should return valid data`() = runTest {
        coEvery { mockMovieImageApi.getMovieImages(1) } returns mockkRelaxed()

        repository.getImages(Movie.Id(1))

        coVerify {
            mockMovieImageApi.getMovieImages(movieId = 1)
        }
    }

    @Test(expected = UnknownHostException::class)
    fun `should return error`() = runTest {
        coEvery { mockMovieImageApi.getMovieImages(1) } throws UnknownHostException()

        repository.getImages(Movie.Id(1))

        coVerify {
            mockMovieImageApi.getMovieImages(movieId = 1)
        }
    }
}