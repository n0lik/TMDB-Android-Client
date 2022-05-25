package com.n0lik.sample.movie.presentation.popular

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.n0lik.sample.common.AppDependency
import com.n0lik.sample.common.di.Injectable
import com.n0lik.sample.common.ui.ext.removeAdapterOnDetach
import com.n0lik.sample.common.ui.widget.recyclerview.GridDecoration
import com.n0lik.sample.genres.di.DaggerGenreComponent
import com.n0lik.sample.movie.DaggerPopularMovieComponent
import com.n0lik.sample.movie.impl.R
import com.n0lik.sample.movie.impl.databinding.PopularMoviesFragmentBinding
import com.n0lik.sample.movie.model.Movie
import javax.inject.Inject

private const val GRID_SIZE = 4

class PopularMoviesFragment @Inject constructor() : Fragment(R.layout.popular_movies_fragment), Injectable {

    private var _binding: PopularMoviesFragmentBinding? = null
    private val binding
        get() = _binding!!

    private val movieAdapter = PopularMovieAdapter { movie ->
        openMovieDetail(movie)
    }

    private fun openMovieDetail(it: Movie) {
        findNavController().navigate(
            R.id.action_open_detail_from_popular,
            Bundle().apply {
                putInt("movieId", it.id!!.id)
            }
        )
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel: PopularMoviesViewModel by viewModels { viewModelFactory }

    override fun inject(dependency: AppDependency) {
        val genreDependency = DaggerGenreComponent.factory()
            .build(dependency)
        DaggerPopularMovieComponent.factory()
            .build(dependency, genreDependency)
            .inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = PopularMoviesFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUi()
    }

    private fun setupUi() {
        binding.popularMoviesList.apply {
            layoutManager = GridLayoutManager(context, GRID_SIZE)
            addItemDecoration(GridDecoration(32, GRID_SIZE))
            adapter = movieAdapter.withLoadStateFooter(
                PagingLoadStateAdapter(movieAdapter)
            )
            removeAdapterOnDetach()
        }
        lifecycleScope.launchWhenStarted {
            viewModel.popularMoviesFlow.collect { page ->
                movieAdapter.submitData(page)
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}