package com.n0lik.sample.common.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class DelegateAdapter<T : Any>(
    vararg delegates: AdapterDelegate<List<T>>,
    diffUtil: DiffUtil.ItemCallback<T>
) : AsyncListDifferDelegationAdapter<T>(diffUtil) {

    init {
        delegates.forEach { delegatesManager.addDelegate(it) }
    }
}