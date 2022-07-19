package com.n0lik.sample.movie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.domain.ConfigRepository
import com.n0lik.sample.common.mapper.Mapper1
import com.n0lik.sample.common.model.ImageConfig
import com.n0lik.sample.movie.data.api.MovieKtorApi
import com.n0lik.sample.movie.data.api.START_PAGE_NUMBER
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.model.Movie
import io.ktor.utils.io.errors.IOException
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class PopularMovieDataSource @Inject constructor(
    private val appDispatcher: AppDispatcher,
    private val movieApi: MovieKtorApi,
    private val configRepository: ConfigRepository,
    private val mapper: Mapper1<PagedListDto<MovieDto>, ImageConfig, LoadResult<Int, Movie>>
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return state.anchorPosition ?: getFirstPageNumber()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return withContext(appDispatcher.io) {
            try {
                val pageNumber = params.key ?: getFirstPageNumber()

                val config = async { configRepository.getImageConfig() }
                val response = async { movieApi.getPopularMovies(pageNumber) }

                mapper.mapTo(response.await(), config.await())
            } catch (e: IOException) {
                LoadResult.Error(e)
            }
        }
    }

    private fun getFirstPageNumber() = START_PAGE_NUMBER
}