package com.n0lik.sample.movie.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.common.di.Injectable
import com.n0lik.sample.common.model.Image
import com.n0lik.sample.common.ui.adapter.DelegateAdapter
import com.n0lik.sample.common.ui.adapter.diffUtilBuilder
import com.n0lik.sample.common.ui.ext.loadTmdbImage
import com.n0lik.sample.common.ui.ext.removeAdapterOnDetach
import com.n0lik.sample.common.ui.ext.visibleIf
import com.n0lik.sample.common.ui.utils.CropOptions
import com.n0lik.sample.common.ui.widget.recyclerview.SpacingItemDecorator
import com.n0lik.sample.genres.di.DaggerGenreComponent
import com.n0lik.sample.movie.DaggerMovieDetailComponent
import com.n0lik.sample.movie.impl.R
import com.n0lik.sample.movie.impl.databinding.MovieDetailFragmentBinding
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.presentation.delegates.movieAdapterDelegate
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@Suppress("TooManyFunctions")
class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel: MovieDetailViewModel by viewModels { viewModelFactory }
    private var favoriteMenuItem: MenuItem? = null
    private val posterCornerRadius: Int by lazy(LazyThreadSafetyMode.NONE) {
        resources.getDimensionPixelSize(R.dimen.movie_detail_poster_corner_radius)
    }

    private val similarMoviesAdapter = DelegateAdapter(
        movieAdapterDelegate { openMovieDetail(it) },
        diffUtil = diffUtilBuilder({ old: Movie, new: Movie -> old.id == new.id })
    )

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.movie_detail_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        favoriteMenuItem = menu.findItem(R.id.favorite_menu_item)
        observeFavoriteState()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.favorite_menu_item) {
            viewModel.onFavoriteClick()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding.movieDetailSimilarMovies.apply {
            adapter = similarMoviesAdapter
            addItemDecoration(SpacingItemDecorator(resources.getDimensionPixelSize(R.dimen.movie_detail_item_space)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            removeAdapterOnDetach()
        }
        observeUiState()
    }

    private fun openMovieDetail(movie: Movie) {
        findNavController().navigate(
            R.id.action_open_detail_from_detail,
            Bundle().apply {
                putInt("movieId", movie.id.id)
            }
        )
    }

    private fun observeUiState() {
        binding.movieDetailSwipeRefresh.setOnRefreshListener { viewModel.load() }
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { uiModel -> render(uiModel) }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    override fun inject(dependency: AppDependency) {
        val movieId = requireArguments().get("movieId") as Int
        val genreComponent = DaggerGenreComponent.factory()
            .build(dependency)
        DaggerMovieDetailComponent.factory()
            .build(movieId, dependency, genreComponent)
            .inject(this)
    }

    private fun render(uiModel: MovieDetailUiModel) {
        binding.movieDetailSwipeRefresh.isRefreshing = uiModel.state == Loading
        if (uiModel.state is Error) {
            //TODO it will be fixed later
            Toast.makeText(requireContext(), "Some error!", Toast.LENGTH_LONG).show()
        }
        with(binding) {
            showPoster(uiModel.movie?.posterImage)
            showBackdrop(uiModel.movie?.backdropImage)
            movieDetailTitle.text = uiModel.movie?.title
            movieDetailDescription.text = uiModel.movie?.overview
            showSimilarMovies(uiModel.similarMovies)
        }
    }

    private fun showSimilarMovies(movies: List<Movie>?) {
        binding.movieDetailSimilarGroup.visibleIf(!movies.isNullOrEmpty())
        movies?.also { similarMoviesAdapter.setItems(it) }
    }

    private fun observeFavoriteState() {
        lifecycleScope.launchWhenResumed {
            viewModel.viewState
                .map { it.isFavorite }
                .collect { updateFavoriteIcon(it) }
        }
    }

    private fun showPoster(posterImage: Image?) {
        binding.movieDetailPoster.loadTmdbImage(posterImage) {
            cornerRadius = posterCornerRadius
            this
        }
    }

    private fun showBackdrop(backdropImage: Image?) {
        binding.movieHeaderImage.loadTmdbImage(backdropImage) {
            cropOptions = CropOptions.CenterCrop
            this
        }
    }

    private fun updateFavoriteIcon(isFavorite: Boolean) {
        favoriteMenuItem?.apply {
            val drawableResId = if (isFavorite) {
                R.drawable.ic_favorite_checked
            } else {
                R.drawable.ic_favorite
            }
            setIcon(drawableResId)
        }
    }
}