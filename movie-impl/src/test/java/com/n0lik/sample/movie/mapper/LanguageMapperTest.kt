package com.n0lik.sample.movie.mapper

import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.model.Language
import org.junit.Assert
import org.junit.Test

internal class LanguageMapperTest {

    private val mapper = LanguageMapper()

    @Test
    fun `should correctly map genre`() {
        val model = LanguageDto(
            iso = "iso",
            name = "name"
        )
        val expected = Language(
            iso = "iso",
            name = "name"
        )

        val actual = mapper.mapTo(model)

        Assert.assertEquals(expected, actual)
    }

    @Test
    fun `should correctly map list of genre`() {
        val list = listOf(
            LanguageDto(iso = "iso", name = "name_0"),
            LanguageDto(iso = "iso", name = "name_1")
        )
        val expected = listOf(
            Language(iso = "iso", name = "name_0"),
            Language(iso = "iso", name = "name_1")
        )

        val actual = mapper.mapToList(list)

        Assert.assertEquals(expected, actual)
    }
}