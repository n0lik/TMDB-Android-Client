package com.n0lik.sample.common.ui.utils

import com.n0lik.sample.common.model.Image
import com.n0lik.sample.common.ui.utils.TmdbImageUrlProvider.selectSize
import org.junit.Assert.assertEquals
import org.junit.Test

internal class TmdbImageUrlProviderTest {

    private val image = Image.Poster(
        path = "/poster.jpg",
        secureBaseUrl = "https://test.me/",
        sizes = listOf(
            "w160", "w320", "w400", "w500", "original"
        )
    )

    @Test
    fun `should return valid url`() {
        val expected = "https://test.me/w400/poster.jpg"

        val actual = image.selectSize(420, 420)

        assertEquals(expected, actual)
    }

    @Test
    fun `should return valid url when requested smallest size`() {
        val expected = "https://test.me/w160/poster.jpg"

        val actual = image.selectSize(130, 130)

        assertEquals(expected, actual)
    }

    @Test
    fun `should return valid url when requested big size`() {
        val expected = "https://test.me/original/poster.jpg"

        val actual = image.selectSize(1920, 420)

        assertEquals(expected, actual)
    }
}