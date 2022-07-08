package com.n0lik.sample.movie.data

import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.MovieImages

interface MovieImageRepository {

    suspend fun getImages(movieId: Movie.Id): MovieImages
}