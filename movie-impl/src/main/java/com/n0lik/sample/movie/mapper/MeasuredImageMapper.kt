package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.Mapper0
import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import com.n0lik.sample.movie.model.MeasuredImage
import com.n0lik.sample.movie.model.MovieImages
import javax.inject.Inject

internal class MeasuredImageMapper
@Inject constructor() : Mapper0<MovieImagesDto, MovieImages> {

    override fun mapTo(t: MovieImagesDto): MovieImages {
        val posters = t.posters
            .map {
                with(it) {
                    MeasuredImage.Poster(
                        path = filePath,
                        width = width,
                        height = height
                    )
                }
            }
            .sortedBy { it.width }
        val backdrops = t.backdrops
            .map {
                with(it) {
                    MeasuredImage.Backdrop(
                        path = filePath,
                        width = width,
                        height = height
                    )
                }
            }
            .sortedBy { it.width }

        return MovieImages(
            posters = posters,
            backdrops = backdrops
        )
    }
}