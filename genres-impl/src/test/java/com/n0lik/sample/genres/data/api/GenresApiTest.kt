package com.n0lik.sample.genres.data.api

import com.n0lik.common.test.ktor.MockKtorHttpClient
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.data.api.dto.GenresDto
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Test

private const val PATH_GET_GENRES = "/3/genre/movie/list"

@OptIn(ExperimentalCoroutinesApi::class)
internal class GenresApiTest {

    private val mockHttpClient = MockKtorHttpClient.builder {
        headers {
            MockKtorHttpClient.Builder.CONTENT_TYPE_JSON
        }
    }

    private val genresApi = GenresApiImpl(mockHttpClient.get())

    @Test
    fun `check successful loading`() = runTest {
        val jsonResponse = "{\"genres\":[{\"id\":1, \"name\":\"Name\"}]}"
        mockHttpClient.mockOkResponse(PATH_GET_GENRES, jsonResponse)
        val expected = GenresDto(
            genres = listOf(
                GenreDto(
                    id = 1,
                    name = "Name"
                )
            )
        )

        val actual = genresApi.getMovieGenres()

        Assert.assertEquals(expected, actual)
    }

    @Test(expected = UnknownHostException::class)
    fun `check fail call`() = runTest {
        mockHttpClient.mockErrorResponse(PATH_GET_GENRES, UnknownHostException())
        genresApi.getMovieGenres()
    }
}