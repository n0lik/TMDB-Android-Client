package com.n0lik.sample.common.ui.mapper

import com.n0lik.sample.common.mapper.DateMapper
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

internal class DateMapperTest {

    private val dateMapper = DateMapper()

    @Test
    fun `should successful map date`() {
        val expected = "2022"

        val actual = dateMapper.mapToReleaseDate("2022-01-01")

        assertEquals(expected, actual)
    }

    @Test
    fun `should return null when date format is wrong`() {
        val actual = dateMapper.mapToReleaseDate("01-2022")

        assertNull(actual)
    }

    @Test
    fun `should return null when date is null`() {
        val actual = dateMapper.mapToReleaseDate(null)

        assertNull(actual)
    }
}