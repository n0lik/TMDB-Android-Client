package com.n0lik.sample.movie.data

import androidx.paging.PagingData
import com.n0lik.sample.movie.model.Movie
import kotlinx.coroutines.flow.Flow

interface PopularMovieRepository {

    suspend fun getPopularMovies(): Flow<PagingData<Movie>>
}