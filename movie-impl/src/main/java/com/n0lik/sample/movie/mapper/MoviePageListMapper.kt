package com.n0lik.sample.movie.mapper

import androidx.paging.PagingSource
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.PagedListDto
import com.n0lik.sample.movie.model.Movie
import javax.inject.Inject

private const val DEFAULT_PAGE_SIZE = 20

internal class MoviePageListMapper
@Inject constructor(
    private val mapper: MapperTo<MovieDto, Movie>
) : MapperTo<PagedListDto<MovieDto>, PagingSource.LoadResult<Int, Movie>> {

    override fun mapTo(model: PagedListDto<MovieDto>): PagingSource.LoadResult<Int, Movie> {
        var nextPageNumber: Int? = null
        val currentPageNumber = model.page
        val totalPages = model.totalPages

        if ((currentPageNumber * DEFAULT_PAGE_SIZE) < totalPages) {
            nextPageNumber = currentPageNumber + 1
        }

        val data = mapper.mapToList(model.data)

        return PagingSource.LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = nextPageNumber
        )
    }
}