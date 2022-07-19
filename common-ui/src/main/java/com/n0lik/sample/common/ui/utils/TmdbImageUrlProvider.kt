package com.n0lik.sample.common.ui.utils

import com.n0lik.sample.common.model.Image

@Suppress("NestedBlockDepth", "ComplexMethod", "ReturnCount")
object TmdbImageUrlProvider {

    private val imageSizePattern = "w(\\d+)$".toRegex()
    private val imageSizeHeightPattern = "h(\\d+)$".toRegex()

    fun Image.selectSize(imageWidth: Int, imageHeight: Int): String? {
        return getSizeByMeasure(this, imageWidth, imageHeight)?.buildImageUrl(secureBaseUrl, path)
    }

    /**
     * Source:
     * https://github.com/chrisbanes/tivi/blob/master/tmdb/src/main/java/app/tivi/tmdb/TmdbImageUrlProvider.kt
     */

    /**
     * Select suitable path from available sizes in {@class #com.n0lik.sample.common.model.TmdbConfig}
     *
     * @param image - image
     * @param width - imageView's width
     * @param height - imageView's height
     * @return path string. Examples: "w320", "w500", "origin"
     */
    private fun getSizeByMeasure(image: Image, width: Int, height: Int): String? {
        with(image) {
            var previousSize: String? = null

            var previousWidth = 0
            var previousHeight = 0

            for (i in sizes.indices) {
                val size = sizes[i]

                val sizeWidth = extractWidthAsIntFrom(size)
                val sizeHeight = extractHeightAsIntFrom(size)

                if (sizeWidth != null) {
                    if (sizeWidth > width) {
                        if (previousSize != null && width > (previousWidth + sizeWidth) / 2) {
                            return size
                        } else if (previousSize != null) {
                            return previousSize
                        }
                    } else if (i == sizes.size - 1) {
                        // If we get here then we're larger than the last bucket
                        if (width < sizeWidth * 2) {
                            return size
                        }
                    }
                    previousWidth = sizeWidth
                } else if (sizeHeight != null) {
                    if (sizeHeight > height) {
                        if (previousSize != null && height > (previousHeight + sizeHeight) / 2) {
                            return size
                        } else if (previousSize != null) {
                            return previousSize
                        }
                    } else if (i == sizes.size - 1) {
                        // If we get here then we're larger than the last bucket
                        if (height < sizeHeight * 2) {
                            return size
                        }
                    }
                    previousHeight = sizeHeight
                }
                previousSize = size
            }

            return (previousSize ?: sizes.lastOrNull())
        }
    }

    private fun extractHeightAsIntFrom(size: String): Int? {
        return imageSizeHeightPattern.matchEntire(size)?.groups?.get(1)?.value?.toInt()
    }

    private fun extractWidthAsIntFrom(size: String): Int? {
        return imageSizePattern.matchEntire(size)?.groups?.get(1)?.value?.toInt()
    }

    private fun String.buildImageUrl(basePath: String?, imgPath: String?): String? {
        return if (basePath.isNullOrBlank() || this.isBlank() || imgPath.isNullOrBlank()) {
            null
        } else {
            "$basePath$this$imgPath"
        }
    }
}