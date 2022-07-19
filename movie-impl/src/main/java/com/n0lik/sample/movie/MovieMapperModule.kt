package com.n0lik.sample.movie

import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.common.mapper.Mapper1
import com.n0lik.sample.common.model.ImageConfig
import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.mapper.LanguageMapper
import com.n0lik.sample.movie.mapper.MovieMapper
import com.n0lik.sample.movie.mapper.ProductionCompanyMapper
import com.n0lik.sample.movie.model.Language
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.ProductionCompany
import dagger.Binds
import dagger.Module

@Module
internal interface MovieMapperModule {

    @Binds
    fun provideMovieMapper(impl: MovieMapper): Mapper1<MovieDto, ImageConfig, Movie>

    @Binds
    fun provideProductionCompanyMapper(
        impl: ProductionCompanyMapper
    ): Mapper0<ProductionCompanyDto, ProductionCompany>

    @Binds
    fun provideLanguageMapper(impl: LanguageMapper): Mapper0<LanguageDto, Language>
}