package com.n0lik.sample.movie.presentation.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.n0lik.sample.common.ui.utils.load
import com.n0lik.sample.movie.impl.databinding.MovieItemBinding
import com.n0lik.sample.movie.model.Movie

class PopularMovieAdapter(
    private val onClick: MovieClickListener
) : PagingDataAdapter<Movie, MovieViewHolder>(diffCallback = DIFF) {

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.also {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return MovieViewHolder(onClick, binding)
    }

    companion object {

        private val DIFF = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean = oldItem == newItem
        }
    }
}

typealias MovieClickListener = (Movie) -> Unit

class MovieViewHolder
constructor(
    private val onClick: MovieClickListener,
    private val binding: MovieItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
        with(binding) {
            root.setOnClickListener { onClick.invoke(movie) }
            itemMoviePoster.load(movie.posterPath)
        }
    }
}