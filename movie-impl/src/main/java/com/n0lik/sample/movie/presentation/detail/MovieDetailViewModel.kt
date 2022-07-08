package com.n0lik.sample.movie.presentation.detail

import androidx.lifecycle.viewModelScope
import com.n0lik.sample.common.ui.CommonViewModel
import com.n0lik.sample.movie.domain.MovieDetailInteractor
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.model.TmdbImage
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel
@Inject constructor(
    private val movieId: Int,
    private val movieDetailInteractor: MovieDetailInteractor
) : CommonViewModel() {

    private val _viewState = MutableStateFlow(initState())
    private var job: Job? = null

    val viewState: Flow<MovieDetailUiModel> = _viewState

    init {
        load()
    }

    override fun handleError(throwable: Throwable) {
        _viewState.tryEmit(_viewState.value.copy(state = Error(throwable)))
    }

    fun load() {
        job?.cancel()
        job = viewModelScope.launch(errorHandler) {
            _viewState.emit(_viewState.value.copy(state = Loading))
            movieDetailInteractor.getMovieDetail(Movie.Id(movieId))
                .catch { handleError(it) }
                .collect {
                    _viewState.emit(
                        MovieDetailUiModel.success(
                            movie = it.movie,
                            similarMovies = it.similarMovies,
                            isFavorite = it.isFavorite,
                            backdrops = it.backdrops,
                            posters = it.posters
                        )
                    )
                }
        }
    }

    fun onFavoriteClick() {
        viewModelScope.launch {
            val detailUiModel = _viewState.value
            val newFavoriteState = !detailUiModel.isFavorite
            detailUiModel.movie?.let {
                movieDetailInteractor.changeFavoriteState(newFavoriteState, it.id)
            }
        }
    }

    private fun initState(): MovieDetailUiModel {
        return MovieDetailUiModel.loading()
    }
}

data class MovieDetailUiModel(
    val isFavorite: Boolean,
    val state: LoadingState,
    val movie: Movie?,
    val similarMovies: List<Movie>?,
    val backdrops: List<TmdbImage>?,
    val posters: List<TmdbImage>?
) {

    companion object {

        fun loading() = MovieDetailUiModel(
            isFavorite = false,
            state = Loading,
            movie = null,
            similarMovies = null,
            posters = null,
            backdrops = null
        )

        fun success(
            movie: Movie?,
            similarMovies: List<Movie>?,
            isFavorite: Boolean,
            backdrops: List<TmdbImage>?,
            posters: List<TmdbImage>?
        ) = MovieDetailUiModel(
            isFavorite, Success, movie, similarMovies, backdrops, posters
        )
    }
}

sealed class LoadingState
object Success : LoadingState()
object Loading : LoadingState()
data class Error(val throwable: Throwable) : LoadingState()