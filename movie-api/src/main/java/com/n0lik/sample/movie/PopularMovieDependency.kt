package com.n0lik.sample.movie

import com.n0lik.sample.movie.data.PopularMovieRepository

interface PopularMovieDependency {

    fun providePopularMovieRepository(): PopularMovieRepository
}