package com.n0lik.sample.movie.mapper

import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import com.n0lik.sample.movie.model.MeasuredImage
import com.n0lik.sample.movie.model.MovieImages
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MeasuredImageMapperTest {

    private val mapper = MeasuredImageMapper()

    @Test
    fun `should map correctly`() {
        val model = MovieImagesDto(
            backdrops = listOf(
                MovieImagesDto.ImageDto(
                    filePath = "/pathBackdrop",
                    width = 1,
                    height = 2
                )
            ),
            posters = listOf(
                MovieImagesDto.ImageDto(
                    filePath = "/pathPosters",
                    width = 3,
                    height = 4
                )
            )
        )
        val expected = MovieImages(
            backdrops = listOf(
                MeasuredImage.Backdrop(
                    path = "/pathBackdrop",
                    width = 1,
                    height = 2
                )
            ),
            posters = listOf(
                MeasuredImage.Poster(
                    path = "/pathPosters",
                    width = 3,
                    height = 4
                )
            )
        )

        val actual = mapper.mapTo(model)

        assertEquals(expected, actual)
    }
}