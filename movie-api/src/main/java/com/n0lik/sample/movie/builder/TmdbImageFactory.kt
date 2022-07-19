package com.n0lik.sample.movie.builder

import com.n0lik.sample.common.model.Image
import com.n0lik.sample.common.model.ImageConfig
import javax.inject.Inject

class TmdbImageFactory
@Inject constructor() {

    fun createPoster(
        config: ImageConfig,
        path: String?
    ) = Image.Poster(
        path = path ?: "",
        sizes = config.posterSizes,
        secureBaseUrl = config.secureBaseUrl
    )

    fun createBackdrop(
        config: ImageConfig,
        path: String?,
    ) = Image.Backdrop(
        path = path ?: "",
        sizes = config.backdropSizes,
        secureBaseUrl = config.secureBaseUrl
    )
}