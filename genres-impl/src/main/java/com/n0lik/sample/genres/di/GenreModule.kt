package com.n0lik.sample.genres.di

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.genres.data.GenreRepository
import com.n0lik.sample.genres.data.GenreRepositoryImpl
import com.n0lik.sample.genres.data.api.GenresApi
import com.n0lik.sample.genres.data.api.GenresApiImpl
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.mapper.GenreMapper
import com.n0lik.sample.genres.model.Genre
import dagger.Binds
import dagger.Module

@Module
internal interface GenreModule {

    @Binds
    fun provideGenreMapper(impl: GenreMapper): MapperTo<GenreDto, Genre>

    @Binds
    fun provideGenreRepository(impl: GenreRepositoryImpl): GenreRepository

    @Binds
    fun provideGenreApi(impl: GenresApiImpl): GenresApi
}