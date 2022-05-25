package com.n0lik.sample.movie.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
import javax.inject.Inject

class MovieDetailFragment : Fragment(R.layout.movie_detail_fragment), Injectable {

    private var _binding: MovieDetailFragmentBinding? = null
    private val binding
        get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel: MovieDetailViewModel by viewModels { viewModelFactory }

    private val posterCornerRadius: Int by lazy(LazyThreadSafetyMode.NONE) {
        resources.getDimensionPixelSize(R.dimen.movie_detail_poster_corner_radius)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = MovieDetailFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launchWhenCreated {
            viewModel.viewState.collect {
                with(binding) {
                    movieHeaderImage.loadImage(it.posterUrl) {
                        cropOptions = CropOptions.CenterCrop
                        this
                    }
                    moviePoster.loadImage(it.posterUrl) {
                        cornerRadius = posterCornerRadius
                        this
                    }
                    movieTitle.text = it.title
                    movieDescription.text = it.overview
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
}