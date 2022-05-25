package com.n0lik.sample.movie

import com.n0lik.sample.movie.data.MovieRepository

interface MovieDependency {

    fun provideMovieRepository(): MovieRepository
}