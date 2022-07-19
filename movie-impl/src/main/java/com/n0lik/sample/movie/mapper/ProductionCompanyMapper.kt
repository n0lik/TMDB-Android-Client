package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.model.ProductionCompany
import javax.inject.Inject

internal class ProductionCompanyMapper
@Inject constructor() : Mapper0<ProductionCompanyDto, ProductionCompany> {

    override fun mapTo(t: ProductionCompanyDto): ProductionCompany {
        return ProductionCompany(
            id = t.id,
            logoPath = t.logoPath,
            name = t.name,
            originCountry = t.originCountry
        )
    }
}