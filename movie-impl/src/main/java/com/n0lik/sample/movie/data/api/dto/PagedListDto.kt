package com.n0lik.sample.movie.data.api.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PagedListDto<T>(
    @SerialName("page")
    val page: Int,
    @SerialName("results")
    val data: List<T>,
    @SerialName("total_pages")
    val totalPages: Int,
    @SerialName("total_results")
    val totalResults: Int
)