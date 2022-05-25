package com.n0lik.sample.genres.data

import com.n0lik.common.test.dispatcher.TestAppDispatcher
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.genres.data.api.GenresApi
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.data.api.dto.GenresDto
import com.n0lik.sample.genres.model.Genre
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
class GenreRepositoryImplTest {

    private val mapper = mockk<MapperTo<GenreDto, Genre>>()
    private val dispatcher = TestAppDispatcher()
    private val mockApi = mockk<GenresApi>()

    private val repository = GenreRepositoryImpl(
        dispatcher = dispatcher,
        genresApi = mockApi,
        mapper = mapper
    )

    @Test
    fun `should successful load`() = runTest {
        val genresDto = GenresDto(emptyList())
        coEvery { mockApi.getMovieGenres() } returns genresDto
        every { mapper.mapToList(emptyList()) } returns emptyList()
        val expected = emptyList<Genre>()

        val actual = repository.getGenres()

        coVerify {
            mockApi.getMovieGenres()
            mapper.mapToList(emptyList())
        }
        assertEquals(expected, actual)
    }

    @Test
    fun `should return error when api fail`() = runTest {
        val expected = UnknownHostException()
        coEvery { mockApi.getMovieGenres() } throws UnknownHostException()
        every { mapper.mapToList(emptyList()) } returns emptyList()

        try {
            repository.getGenres()
        } catch (actual: Exception) {
            assertEquals(expected::class, actual::class)
        }

        coVerify {
            mockApi.getMovieGenres()
        }
        verify(exactly = 0) {
            mapper.mapToList(any())
        }
    }

    @Test
    fun `should return error when invalid model`() = runTest {
        val genresDto = GenresDto(emptyList())
        coEvery { mockApi.getMovieGenres() } returns genresDto
        every { mapper.mapToList(emptyList()) } throws NullPointerException()
        val expected = NullPointerException()

        try {
            repository.getGenres()
        } catch (actual: Exception) {
            assertEquals(expected::class, actual::class)
        }

        coVerify {
            mockApi.getMovieGenres()
            mapper.mapToList(emptyList())
        }
    }
}