package com.n0lik.sample.genres.mapper

import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre
import org.junit.Assert
import org.junit.Test

internal class GenreMapperTest {

    private val mapper = GenreMapper()

    @Test
    fun `should correctly map genre`() {
        val model = GenreDto(
            id = 1,
            name = "name"
        )
        val expected = Genre(
            genreId = Genre.Id(1),
            name = "name"
        )

        val actual = mapper.mapTo(model)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `should correctly map list of genre item`() {
        val list = listOf(
            GenreDto(id = 1, name = "name"),
            GenreDto(id = 2, name = "name")
        )
        val expected = listOf(
            Genre(genreId = Genre.Id(1), name = "name"),
            Genre(genreId = Genre.Id(2), name = "name")
        )

        val actual = mapper.mapToList(list)

        Assert.assertEquals(expected, actual)
    }
}