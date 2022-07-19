package com.n0lik.sample.movie.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.n0lik.sample.movie.data.api.PAGE_SIZE
import com.n0lik.sample.movie.data.paging.PopularMovieDataSource
import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

private const val PREFETCH_DISTANCE = 5

internal class PopularMovieRepositoryImpl
@Inject constructor(
    private val popularMovieDataSource: PopularMovieDataSource
) : PopularMovieRepository {

    override suspend fun getPopularMovies(): Flow<PagingData<Movie>> {
        val pageConfig = PagingConfig(
            pageSize = PAGE_SIZE,
            prefetchDistance = PREFETCH_DISTANCE,
            initialLoadSize = PAGE_SIZE
        )
        return Pager(
            config = pageConfig,
            pagingSourceFactory = { popularMovieDataSource }
        ).flow
    }
}