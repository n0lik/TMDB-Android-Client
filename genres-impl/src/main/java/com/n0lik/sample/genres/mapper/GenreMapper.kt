package com.n0lik.sample.genres.mapper

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import javax.inject.Inject

internal class GenreMapper
@Inject constructor() : MapperTo<GenreDto, Genre> {

    override fun mapTo(model: GenreDto): Genre {
        return Genre(
            genreId = Genre.Id(model.id),
            name = model.name
        )
    }
}