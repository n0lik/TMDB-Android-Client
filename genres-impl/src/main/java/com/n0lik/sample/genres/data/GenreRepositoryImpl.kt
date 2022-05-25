package com.n0lik.sample.genres.data

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.genres.data.api.GenresApi
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import javax.inject.Inject
import kotlinx.coroutines.withContext

internal class GenreRepositoryImpl
@Inject constructor(
    private val dispatcher: AppDispatcher,
    private val genresApi: GenresApi,
    private val mapper: MapperTo<GenreDto, Genre>
) : GenreRepository {

    override suspend fun getGenres(): List<Genre> {
        return withContext(dispatcher.IO) {
            genresApi.getMovieGenres().let {
                mapper.mapToList(it.genres)
            }
        }
    }
}