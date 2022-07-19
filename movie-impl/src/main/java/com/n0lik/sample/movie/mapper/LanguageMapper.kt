package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.model.Language
import javax.inject.Inject

internal class LanguageMapper
@Inject constructor() : Mapper0<LanguageDto, Language> {

    override fun mapTo(t: LanguageDto): Language {
        return Language(
            iso = t.iso,
            name = t.name
        )
    }
}