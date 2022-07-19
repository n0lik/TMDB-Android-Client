package com.n0lik.sample.common.data.api

import com.n0lik.sample.common.data.dto.ConfigImagesDto

internal interface ConfigApi {

    suspend fun getConfig(): ConfigImagesDto.ConfigDto
}