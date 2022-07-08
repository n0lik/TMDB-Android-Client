package com.n0lik.sample.movie.presentation.delegates

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import com.n0lik.sample.common.ui.utils.load
import com.n0lik.sample.movie.impl.databinding.MovieItemBinding
import com.n0lik.sample.movie.model.Movie

fun movieAdapterDelegate(
    onClick: (Movie) -> Unit
) = adapterDelegateViewBinding<Movie, Movie, MovieItemBinding>(
    viewBinding = { layoutInflater, root -> MovieItemBinding.inflate(layoutInflater, root, false) }
) {
    bind {
        binding.apply {
            itemMoviePoster.load(item.posterPath)
            root.setOnClickListener { onClick(item) }
        }
    }
}