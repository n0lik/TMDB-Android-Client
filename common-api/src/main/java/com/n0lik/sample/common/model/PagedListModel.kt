package com.n0lik.sample.common.model

open class PagedListModel<T>
constructor(
    val page: Int,
    val totalPages: Int,
    val data: List<T>
)