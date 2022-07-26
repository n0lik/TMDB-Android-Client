package com.n0lik.sample.movie.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.n0lik.sample.common.domain.ConfigRepository
import com.n0lik.sample.common.mapper.Mapper1
import com.n0lik.sample.common.model.ImageConfig
import com.n0lik.sample.movie.data.api.MovieKtorApi
import com.n0lik.sample.movie.data.api.START_PAGE_NUMBER
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.ensureActive
import javax.inject.Inject
@SuppressWarnings("TooGenericExceptionCaught")
internal class PopularMovieDataSource @Inject constructor(
    private val movieApi: MovieKtorApi,
    private val configRepository: ConfigRepository,
    private val mapper: Mapper1<PagedListDto<MovieDto>, ImageConfig, LoadResult<Int, Movie>>
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int {
        return state.anchorPosition ?: getFirstPageNumber()
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            coroutineScope {
                ensureActive()
                val pageNumber = params.key ?: getFirstPageNumber()
                val config = async { configRepository.getImageConfig() }
                val response = async { movieApi.getPopularMovies(pageNumber) }

                mapper.mapTo(response.await(), config.await())
            }
        } catch (t: Throwable) {
            LoadResult.Error(t)
        }
    }

    private fun getFirstPageNumber() = START_PAGE_NUMBER
}