package com.n0lik.sample.movie.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.MovieKtorApi
import com.n0lik.sample.movie.data.api.PAGE_SIZE
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.data.paging.PopularMovieDataSource
import com.n0lik.sample.movie.model.Movie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

private const val PREFETCH_DISTANCE = 5

internal class PopularMovieRepositoryImpl
@Inject constructor(
    private val movieKtorApi: MovieKtorApi,
    private val moviePageMapper: MapperTo<PagedListDto<MovieDto>, PagingSource.LoadResult<Int, Movie>>
) : PopularMovieRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        val pageConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = PAGE_SIZE
        )
        return Pager(
            config = pageConfig,
            pagingSourceFactory = {
                PopularMovieDataSource(
                    movieApi = movieKtorApi,
                    mapper = moviePageMapper
                )
            }
        ).flow
    }
}