package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.model.Language
import javax.inject.Inject

internal class LanguageMapper
@Inject constructor() : MapperTo<LanguageDto, Language> {

    override fun mapTo(model: LanguageDto): Language {
        return Language(
            iso = model.iso,
            name = model.name
        )
    }
}