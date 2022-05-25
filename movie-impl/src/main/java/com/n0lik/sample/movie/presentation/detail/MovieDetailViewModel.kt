package com.n0lik.sample.movie.presentation.detail

import androidx.lifecycle.viewModelScope
import com.n0lik.sample.common.ui.CommonViewModel
import com.n0lik.sample.movie.data.MovieRepository
import com.n0lik.sample.movie.model.Movie
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MovieDetailViewModel
@Inject constructor(
    private val movieId: Int,
    private val movieRepository: MovieRepository
) : CommonViewModel() {

    private val _viewState = MutableStateFlow(MovieDetailUiModel())
    val viewState: Flow<MovieDetailUiModel> = _viewState

    private var job: Job? = null

    private val TAG = "MovieViewModel"

    init {
        load()
    }

    fun load() {
        job?.cancel()
        job = viewModelScope.launch(Dispatchers.IO) {
            try {
                val movie = movieRepository.getMovie(Movie.Id(movieId))
                val uiModel = MovieDetailUiModel(
                    posterUrl = movie?.posterPath,
                    title = movie?.title,
                    overview = movie?.overview
                )
                _viewState.emit(uiModel)
            } catch (t: Throwable) {
            }
        }
    }
}

data class MovieDetailUiModel(
    val posterUrl: String? = null,
    val title: String? = null,
    val overview: String? = null
)

sealed class MovieState {
    class Success(val movie: Movie) : MovieState()
    object Loading : MovieState()
    class Error(t: Throwable) : MovieState()
}