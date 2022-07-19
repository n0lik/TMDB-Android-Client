package com.n0lik.sample.movie.mapper

import androidx.paging.PagingSource
import com.n0lik.sample.common.mapper.Mapper1
import com.n0lik.sample.common.model.ImageConfig
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.model.Movie
import javax.inject.Inject

private const val DEFAULT_PAGE_SIZE = 20

internal class MoviePageListMapper
@Inject constructor(
    private val mapper: Mapper1<MovieDto, ImageConfig, Movie>
) : Mapper1<PagedListDto<MovieDto>, ImageConfig, PagingSource.LoadResult<Int, Movie>> {

    override fun mapTo(t1: PagedListDto<MovieDto>, t2: ImageConfig): PagingSource.LoadResult<Int, Movie> {
        var nextPageNumber: Int? = null
        val currentPageNumber = t1.page
        val totalPages = t1.totalPages

        if ((currentPageNumber * DEFAULT_PAGE_SIZE) < totalPages) {
            nextPageNumber = currentPageNumber + 1
        }

        val data = mapper.mapToList(t1.data, t2)

        return PagingSource.LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = nextPageNumber
        )
    }
}