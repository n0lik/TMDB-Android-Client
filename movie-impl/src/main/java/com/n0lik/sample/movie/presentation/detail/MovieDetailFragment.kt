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
import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.common.di.Injectable
import com.n0lik.sample.common.ui.utils.CropOptions
import com.n0lik.sample.common.ui.utils.loadImage
import com.n0lik.sample.genres.di.DaggerGenreComponent
import com.n0lik.sample.movie.DaggerMovieDetailComponent
import com.n0lik.sample.movie.impl.R
import com.n0lik.sample.movie.impl.databinding.MovieDetailFragmentBinding
import com.n0lik.sample.movie.model.TmdbImage
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
        observeUiState()
    }

    private fun observeUiState() {
        binding.movieDetailSwipeRefresh.setOnRefreshListener { viewModel.load() }
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect { uiModel ->
                binding.movieDetailSwipeRefresh.isRefreshing = uiModel.state == Loading
                when (uiModel.state) {
                    is Error -> {
                        //TODO it will be fixed later
                        Toast.makeText(requireContext(), "Some error!", Toast.LENGTH_LONG).show()
                    }
                    is Success -> render(uiModel)
                    else -> Unit
                }
            }
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
        with(binding) {
            loadPoster(uiModel.movie?.posterPath)
            loadBackdrop(uiModel.backdrops)
            movieTitle.text = uiModel.movie?.title
            movieDescription.text = uiModel.movie?.overview
        }
    }

    private fun observeFavoriteState() {
        lifecycleScope.launchWhenResumed {
            viewModel.viewState
                .map { it.isFavorite }
                .collect { updateFavoriteIcon(it) }
        }
    }

    private fun loadPoster(url: String?) {
        url?.also {
            binding.moviePoster.loadImage(it) {
                cornerRadius = posterCornerRadius
                this
            }
        }
    }

    private fun loadBackdrop(images: List<TmdbImage>?) {
        images?.firstOrNull()?.getFullPath()?.also {
            binding.movieHeaderImage.loadImage(it) {
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
}