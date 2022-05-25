package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.model.ProductionCompany
import javax.inject.Inject

internal class ProductionCompanyMapper
@Inject constructor() : MapperTo<ProductionCompanyDto, ProductionCompany> {

    override fun mapTo(model: ProductionCompanyDto): ProductionCompany {
        return ProductionCompany(
            id = model.id,
            logoPath = model.logoPath,
            name = model.name,
            originCountry = model.originCountry
        )
    }
}