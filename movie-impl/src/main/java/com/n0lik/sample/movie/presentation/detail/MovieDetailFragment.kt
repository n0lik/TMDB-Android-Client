package com.n0lik.sample.movie.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
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
import com.google.android.material.chip.Chip
import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.common.di.Injectable
import com.n0lik.sample.common.model.Image
import com.n0lik.sample.common.ui.adapter.DelegateAdapter
import com.n0lik.sample.common.ui.adapter.diffUtilBuilder
import com.n0lik.sample.common.ui.ext.invisibleIf
import com.n0lik.sample.common.ui.ext.loadTmdbImage
import com.n0lik.sample.common.ui.ext.removeAdapterOnDetach
import com.n0lik.sample.common.ui.ext.visibleIf
import com.n0lik.sample.common.ui.utils.CropOptions
import com.n0lik.sample.common.ui.widget.recyclerview.SpacingItemDecorator
import com.n0lik.sample.genres.di.DaggerGenreComponent
import com.n0lik.sample.genres.model.Genre
import com.n0lik.sample.movie.DaggerMovieDetailComponent
import com.n0lik.sample.movie.impl.R
import com.n0lik.sample.movie.impl.databinding.MovieDetailFragmentBinding
import com.n0lik.sample.movie.model.Movie
import com.n0lik.sample.movie.presentation.delegates.movieAdapterDelegate
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

    private val similarMoviesAdapter = DelegateAdapter(
        movieAdapterDelegate { openMovieDetail(it) },
        diffUtil = diffUtilBuilder({ old: Movie, new: Movie -> old.id == new.id })
    )

    override fun inject(dependency: AppDependency) {
        val movieId = requireArguments().get("movieId") as Int
        val genreComponent = DaggerGenreComponent.factory()
            .build(dependency)
        DaggerMovieDetailComponent.factory()
            .build(movieId, dependency, genreComponent)
            .inject(this)
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
        setupUi()
        observeUiState()
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }

    private fun setupUi() {
        with(binding.movieDetailSwipeRefresh) {
            setOnRefreshListener { viewModel.load() }
            setOnChildScrollUpCallback { _, _ -> binding.movieDetailScroll.canScrollVertically(-1) }
        }
        binding.movieDetailSimilarMovies.apply {
            adapter = similarMoviesAdapter
            addItemDecoration(SpacingItemDecorator(resources.getDimensionPixelSize(R.dimen.movie_detail_item_space)))
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
            removeAdapterOnDetach()
        }
        with(binding.movieDetailToolbar) {
            setNavigationIcon(R.drawable.ic_arrow_back)
            setNavigationOnClickListener { activity?.onBackPressed() }
            favoriteMenuItem = menu.findItem(R.id.favorite_menu_item)
            setOnMenuItemClickListener {
                if (it.itemId == R.id.favorite_menu_item) {
                    viewModel.onFavoriteClick()
                }
                false
            }
        }
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
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { uiModel -> render(uiModel) }
        }
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
            movieDetailReleaseDate.text = uiModel.movie?.releaseDate
            movieDetailDescription.text = uiModel.movie?.overview
            showSimilarMovies(uiModel.similarMovies)
            showGenres(uiModel.movie?.genres)
        }
        updateFavoriteIcon(uiModel.isFavorite)
    }

    private fun showSimilarMovies(movies: List<Movie>?) {
        binding.movieDetailSimilarGroup.visibleIf(!movies.isNullOrEmpty())
        movies?.also { similarMoviesAdapter.setItems(it) }
    }

    private fun showPoster(posterImage: Image?) {
        binding.movieDetailPosterLayout.apply {
            card.invisibleIf(posterImage == null)
            poster.loadTmdbImage(posterImage)
        }
    }

    private fun showBackdrop(backdropImage: Image?) {
        binding.movieHeaderImage.apply {
            invisibleIf(backdropImage == null)
            loadTmdbImage(backdropImage) {
                cropOptions = CropOptions.CenterCrop
                this
            }
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

    private fun showGenres(genres: List<Genre>?) {
        with(binding.movieDetailGenres) {
            visibleIf(!genres.isNullOrEmpty())
            removeAllViews()
            genres?.forEach {
                addView(createChip(it.name!!))
            }
        }
    }

    private fun createChip(title: String) = Chip(
        requireContext(), null, R.attr.choiceChip
    ).apply {
        text = title
    }
}