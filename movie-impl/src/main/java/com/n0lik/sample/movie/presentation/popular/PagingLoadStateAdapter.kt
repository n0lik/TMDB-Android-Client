package com.n0lik.sample.movie.presentation.popular

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.n0lik.sample.movie.impl.R
import com.n0lik.sample.movie.impl.databinding.ItemNetworkStateBinding

class PagingLoadStateAdapter<T : Any, VH : RecyclerView.ViewHolder>(
    private val adapter: PagingDataAdapter<T, VH>
) : LoadStateAdapter<PagingLoadStateAdapter.NetworkStateItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState) =
        NetworkStateItemViewHolder.create(parent) { adapter.retry() }

    override fun onBindViewHolder(holder: NetworkStateItemViewHolder, loadState: LoadState) = holder.bind(loadState)

    class NetworkStateItemViewHolder
    private constructor(
        private val binding: ItemNetworkStateBinding,
        private val retryCallback: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retryCallback() }
        }

        fun bind(loadState: LoadState) {
            with(binding) {
                progressBar.isVisible = loadState is LoadState.Loading
                retryButton.isVisible = loadState is LoadState.Error
            }
        }

        companion object {

            fun create(parent: ViewGroup, retryAction: () -> Unit): NetworkStateItemViewHolder {
                val binding = ItemNetworkStateBinding.bind(
                    LayoutInflater
                        .from(parent.context)
                        .inflate(R.layout.item_network_state, parent, false)
                )
                return NetworkStateItemViewHolder(binding, retryAction)
            }
        }
    }
}