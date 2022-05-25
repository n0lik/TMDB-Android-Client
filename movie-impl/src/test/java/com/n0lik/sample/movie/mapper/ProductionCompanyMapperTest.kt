package com.n0lik.sample.movie.mapper

import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.model.ProductionCompany
import org.junit.Assert
import org.junit.Test

class ProductionCompanyMapperTest {

    private val mapper = ProductionCompanyMapper()

    @Test
    fun `should return correct item`() {
        val response = ProductionCompanyDto(
            id = 1,
            logoPath = "path",
            name = "name",
            originCountry = "country"
        )
        val expected = ProductionCompany(
            id = 1,
            logoPath = "path",
            name = "name",
            originCountry = "country"
        )

        val actual = mapper.mapTo(response)

        Assert.assertEquals(expected, actual)
    }
}