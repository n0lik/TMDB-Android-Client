package com.n0lik.sample.common.data.api

import com.n0lik.sample.common.data.dto.ConfigImagesDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject

internal class ConfigApiImpl
@Inject constructor(
    private val httpClient: HttpClient
) : ConfigApi {

    override suspend fun getConfig(): ConfigImagesDto.ConfigDto =
        httpClient.get("/3/configuration").body<ConfigImagesDto>().config
}