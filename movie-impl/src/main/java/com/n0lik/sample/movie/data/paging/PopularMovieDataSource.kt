package com.n0lik.sample.movie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.MovieKtorApi
import com.n0lik.sample.movie.data.api.START_PAGE_NUMBER
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.model.Movie

internal class PopularMovieDataSource constructor(
    private val movieApi: MovieKtorApi,
    private val mapper: MapperTo<PagedListDto<MovieDto>, LoadResult<Int, Movie>>
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return state.anchorPosition ?: getFirstPageNumber()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val pageNumber = params.key ?: getFirstPageNumber()
            val response = movieApi.getPopularMovies(pageNumber)

            mapper.mapTo(response)
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    private fun getFirstPageNumber() = START_PAGE_NUMBER
}