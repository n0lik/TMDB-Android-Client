package com.n0lik.sample.movie.mapper

import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import com.n0lik.sample.movie.model.MovieImages
import com.n0lik.sample.movie.model.TmdbImage
import org.junit.Assert.assertEquals
import org.junit.Test

internal class ImageMapperTest {

    private val mapper = ImageMapper()

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
                TmdbImage(
                    imageType = TmdbImage.ImageType.BACKDROP,
                    path = "/pathBackdrop",
                    width = 1,
                    height = 2
                )
            ),
            posters = listOf(
                TmdbImage(
                    imageType = TmdbImage.ImageType.POSTER,
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