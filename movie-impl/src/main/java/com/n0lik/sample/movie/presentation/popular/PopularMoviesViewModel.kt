package com.n0lik.sample.movie.presentation.popular

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.n0lik.sample.common.ui.CommonViewModel
import com.n0lik.sample.movie.data.PopularMovieRepository
import com.n0lik.sample.movie.model.Movie
import javax.inject.Inject
import kotlinx.coroutines.flow.Flow

class PopularMoviesViewModel
@Inject constructor(
    private val popularMovieRepository: PopularMovieRepository
) : CommonViewModel() {

    val popularMoviesFlow: Flow<PagingData<Movie>>
        get() = _popularMoviesFlow
    private lateinit var _popularMoviesFlow: Flow<PagingData<Movie>>

    init {
        getPopularMovies()
    }

    private fun getPopularMovies() {
        launchPagingAsync({
            popularMovieRepository.getPopularMovies()
                .cachedIn(viewModelScope)
        }, {
            _popularMoviesFlow = it
        })
    }
}