package com.n0lik.sample.genres.data

import com.n0lik.sample.common.dispatcher.AppDispatcher
import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.genres.data.api.GenresApi
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class GenreRepositoryImpl
@Inject constructor(
    private val dispatcher: AppDispatcher,
    private val genresApi: GenresApi,
    private val mapper: Mapper0<GenreDto, Genre>
) : GenreRepository {

    override suspend fun getGenres(): List<Genre> {
        return withContext(dispatcher.io) {
            genresApi.getMovieGenres().let {
                mapper.mapToList(it.genres)
            }
        }
    }
}