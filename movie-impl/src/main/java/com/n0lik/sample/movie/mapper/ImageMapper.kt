package com.n0lik.sample.movie.mapper

import com.n0lik.sample.common.mapper.MapperTo
import com.n0lik.sample.movie.data.api.dto.MovieImagesDto
import com.n0lik.sample.movie.model.MovieImages
import com.n0lik.sample.movie.model.TmdbImage
import javax.inject.Inject

internal class ImageMapper
@Inject constructor() : MapperTo<MovieImagesDto, MovieImages> {

    override fun mapTo(model: MovieImagesDto): MovieImages {
        val posters = model.posters.map { mapByType(it, TmdbImage.ImageType.POSTER) }
        val backdrops = model.backdrops.map { mapByType(it, TmdbImage.ImageType.BACKDROP) }

        return MovieImages(
            posters = posters,
            backdrops = backdrops
        )
    }

    private fun mapByType(
        dto: MovieImagesDto.ImageDto,
        type: TmdbImage.ImageType
    ): TmdbImage = TmdbImage(
        imageType = type,
        path = dto.filePath,
        width = dto.width,
        height = dto.height
    )
}