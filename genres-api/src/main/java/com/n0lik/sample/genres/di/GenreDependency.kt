package com.n0lik.sample.genres.di

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.genres.data.GenreRepository
import com.n0lik.sample.genres.data.api.dto.GenreDto
import com.n0lik.sample.genres.model.Genre

interface GenreDependency {

    fun getGenreRepository(): GenreRepository

    fun getGenreMapper(): MapperTo<GenreDto, Genre>
}