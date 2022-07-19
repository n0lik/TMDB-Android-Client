package com.n0lik.sample.movie.model

sealed class MeasuredImage(
    val path: String,
    val width: Int,
    val height: Int
) {

    class Poster(
        path: String,
        width: Int,
        height: Int
    ) : MeasuredImage(path, width, height)

    class Backdrop(
        path: String,
        width: Int,
        height: Int
    ) : MeasuredImage(path, width, height)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MeasuredImage) return false

        if (path != other.path) return false
        if (width != other.width) return false
        if (height != other.height) return false

        return true
    }

    override fun hashCode(): Int {
        var result = path.hashCode()
        result = 31 * result + width
        result = 31 * result + height
        return result
    }
}

