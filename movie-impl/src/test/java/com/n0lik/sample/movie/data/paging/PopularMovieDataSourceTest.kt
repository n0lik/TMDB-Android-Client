package com.n0lik.sample.movie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.MovieKtorApi
import com.n0lik.sample.movie.data.api.START_PAGE_NUMBER
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.model.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import java.net.UnknownHostException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@ExperimentalCoroutinesApi
internal class PopularMovieDataSourceTest {

    private val movieKtorApiMock = mockk<MovieKtorApi>()
    private val mapper = mockk<MapperTo<PagedListDto<MovieDto>, PagingSource.LoadResult<Int, Movie>>>()

    @Test
    fun `should load first page of data`() = runTest {
        coEvery { movieKtorApiMock.getPopularMovies(1) } returns mockk()
        every { mapper.mapTo(any()) } returns PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = 2
        )
        val pagingSource = PopularMovieDataSource(
            movieApi = movieKtorApiMock,
            mapper = mapper
        )
        val expected = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = 2
        )

        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                loadSize = 20,
                key = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expected, actual)
        coVerify {
            movieKtorApiMock.getPopularMovies(1)
        }
    }

    @Test
    fun `should load next page of data`() = runTest {
        coEvery { movieKtorApiMock.getPopularMovies(any()) } returns mockk()
        val modelList = listOf<PagingSource.LoadResult<Int, Movie>>(
            PagingSource.LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = 2
            ),
            PagingSource.LoadResult.Page(
                data = emptyList(),
                prevKey = null,
                nextKey = 3
            )
        )
        every { mapper.mapTo(any()) } returnsMany modelList
        val pagingSource = PopularMovieDataSource(
            movieApi = movieKtorApiMock,
            mapper = mapper
        )
        val expectedLoadResult0 = PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = 2
        )

        val actualLoadResult0 = pagingSource.load(
            params = PagingSource.LoadParams.Refresh(
                loadSize = 20,
                key = 1,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedLoadResult0, actualLoadResult0)
        coVerify {
            movieKtorApiMock.getPopularMovies(1)
        }

        val expectedLoadResult1 = pagingSource.load(
            params = PagingSource.LoadParams.Append(
                loadSize = 20,
                key = 2,
                placeholdersEnabled = false
            )
        )

        val actualLoadResult1 = pagingSource.load(
            params = PagingSource.LoadParams.Append(
                loadSize = 20,
                key = 2,
                placeholdersEnabled = false
            )
        )

        assertEquals(expectedLoadResult1, actualLoadResult1)
        coVerify {
            movieKtorApiMock.getPopularMovies(2)
        }
    }

    @Test
    fun `should start loading from correct page number`() = runTest {
        coEvery { movieKtorApiMock.getPopularMovies(1) } returns mockk()
        every { mapper.mapTo(any()) } returns PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = 2
        )
        val pagingSource = PopularMovieDataSource(
            movieApi = movieKtorApiMock,
            mapper = mapper
        )
        val expected = START_PAGE_NUMBER

        val actual = pagingSource.getRefreshKey(
            PagingState(
                pages = emptyList(),
                anchorPosition = null,
                config = mockk(),
                leadingPlaceholderCount = 2
            )
        )

        assertEquals(expected, actual)
    }

    @Test
    fun `should return error when api fail`() = runTest {
        coEvery { movieKtorApiMock.getPopularMovies(1) } throws UnknownHostException()
        every { mapper.mapTo(any()) } returns PagingSource.LoadResult.Page(
            data = emptyList(),
            prevKey = null,
            nextKey = 2
        )
        val pagingSource = PopularMovieDataSource(
            movieApi = movieKtorApiMock,
            mapper = mapper
        )
        val expected = UnknownHostException::class

        val actual = (
            pagingSource.load(
                PagingSource.LoadParams.Refresh(
                    loadSize = 20,
                    key = 1,
                    placeholdersEnabled = false
                )
            ) as PagingSource.LoadResult.Error<Int, Movie>
            ).throwable::class

        assertEquals(expected, actual)
    }
}