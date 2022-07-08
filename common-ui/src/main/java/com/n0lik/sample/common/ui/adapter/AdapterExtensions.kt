package com.n0lik.sample.common.ui.adapter

import androidx.recyclerview.widget.DiffUtil

inline fun <T : Any> diffUtilBuilder(
    crossinline itemsTheSame: (old: T, new: T) -> Boolean,
    crossinline contentsTheSame: (old: T, new: T) -> Boolean = { old, new -> old == new },
    crossinline payload: (old: T, new: T) -> Any? = { _, _ -> null }
) = object : DiffUtil.ItemCallback<T>() {

    override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
        return itemsTheSame(oldItem, newItem)
    }

    override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
        return contentsTheSame(oldItem, newItem)
    }

    override fun getChangePayload(oldItem: T, newItem: T): Any? {
        return payload(oldItem, newItem)
    }
}