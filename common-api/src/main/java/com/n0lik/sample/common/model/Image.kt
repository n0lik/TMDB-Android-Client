package com.n0lik.sample.common.model

sealed class Image(
    val path: String,
    val sizes: List<String>,
    val secureBaseUrl: String
) {

    class Poster(
        path: String,
        sizes: List<String>,
        secureBaseUrl: String
    ) : Image(path, sizes, secureBaseUrl)

    class Backdrop(
        path: String,
        sizes: List<String>,
        secureBaseUrl: String
    ) : Image(path, sizes, secureBaseUrl)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Image) return false

        if (path != other.path) return false
        if (sizes != other.sizes) return false
        if (secureBaseUrl != other.secureBaseUrl) return false

        return true
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + sizes.hashCode()
        result = 31 * result + secureBaseUrl.hashCode()
        return result
    }
}