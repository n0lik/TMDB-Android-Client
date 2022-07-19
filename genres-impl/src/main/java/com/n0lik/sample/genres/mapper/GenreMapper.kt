package com.n0lik.sample.genres.mapper

import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import javax.inject.Inject

internal class GenreMapper
@Inject constructor() : Mapper0<GenreDto, Genre> {

    override fun mapTo(t: GenreDto): Genre {
        return Genre(
            genreId = Genre.Id(t.id),
            name = t.name
        )
    }
}