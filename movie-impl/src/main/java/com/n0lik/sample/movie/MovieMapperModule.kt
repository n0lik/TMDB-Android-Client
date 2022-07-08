package com.n0lik.sample.movie

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.dto.LanguageDto
import com.n0lik.sample.movie.data.api.dto.MovieDto
import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import com.n0lik.sample.movie.data.api.dto.ProductionCompanyDto
import com.n0lik.sample.movie.mapper.ImageMapper
import com.n0lik.sample.movie.mapper.LanguageMapper
import com.n0lik.sample.movie.mapper.MovieMapper
import com.n0lik.sample.movie.mapper.ProductionCompanyMapper
import com.n0lik.sample.movie.model.Language
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieImages
import com.n0lik.sample.movie.model.ProductionCompany
import dagger.Binds
import dagger.Module

@Module
internal interface MovieMapperModule {

    @Binds
    fun provideMovieMapper(impl: MovieMapper): MapperTo<MovieDto, Movie>

    @Binds
    fun provideProductionCompanyMapper(
        impl: ProductionCompanyMapper
    ): MapperTo<ProductionCompanyDto, ProductionCompany>

    @Binds
    fun provideLanguageMapper(impl: LanguageMapper): MapperTo<LanguageDto, Language>

    @Binds
    fun provideImageMapper(impl: ImageMapper): MapperTo<MovieImagesDto, MovieImages>
}